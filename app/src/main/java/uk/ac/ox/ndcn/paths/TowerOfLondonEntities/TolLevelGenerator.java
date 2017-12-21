package uk.ac.ox.ndcn.paths.TowerOfLondonEntities;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * Created by appdev on 20/12/2017.
 */
public class TolLevelGenerator {
    private class AbstractPeg {
        public Stack<Float> blocks = new Stack<>();
        int height;
        AbstractPeg(int height){
            this.height = height;
        }

        public boolean isFree(){
            return blocks.size() < height;
        }

        public boolean isLoaded(){
            return blocks.size() > 0;
        }

        public AbstractPeg clone (){
            AbstractPeg clone = new AbstractPeg(height);
            clone.blocks.addAll(this.blocks);
            return clone;
        }
    }

    ArrayList<AbstractPeg> level = new ArrayList<>();
    ArrayList<AbstractPeg> target = new ArrayList<>();
    int numBlocks;

    public TolLevelGenerator(int [] pegHeights, float [] blocks) {

        for(int i = 0; i < pegHeights.length; i ++){
            level.add(new AbstractPeg(pegHeights[i]));
        }

        for(int i = 0; i < blocks.length; i ++){
            chooseRandomFreePeg(level).blocks.push(blocks[i]);
        }

        for(AbstractPeg p : level) {
            target.add(p.clone());
        }

        numBlocks = blocks.length;
    }

    public void shuffleTarget(int difficulty){
        //at the moment this performs d moves. In future prevent revisiting previously visited states by maintaining a history
        for (int i = 0; i < difficulty; i ++){
            chooseRandomFreePeg(target).blocks.push(chooseRandomLoadedPeg(target).blocks.pop());
        }
    }

    public TolLevel ConvertLevel(){
        float [][] pegData = new float[level.size()][2];
        float [][] blockData = new float[numBlocks][3];
        int pegIndex = 0;
        int blockIndex = 0;
        for (AbstractPeg p : level) {
            float pegPosition = (pegIndex+1f)/(level.size()+1f);;
            pegData[pegIndex][0] = pegPosition;
            pegData[pegIndex][1] = p.height;
            pegIndex ++;

            int blockHeight = 1;
            for (float b : p.blocks){
                blockData[blockIndex][0] = pegPosition;
                blockData[blockIndex][1] = blockHeight;
                blockHeight ++;
                blockData[blockIndex][2] = b;
                blockIndex ++;
            }
        }


        float [][] targetBlockData = new float[numBlocks][3];
        int targetPegIndex = 0;
        int targetBlockIndex = 0;
        for (AbstractPeg p : target) {
            float targetPegPosition = (targetPegIndex+1f)/(level.size()+1f);;
            targetPegIndex ++;

            int targetBlockHeight = 1;
            for (float b : p.blocks){
                targetBlockData[targetBlockIndex][0] = targetPegPosition;
                targetBlockData[targetBlockIndex][1] = targetBlockHeight;
                targetBlockHeight ++;
                targetBlockData[targetBlockIndex][2] = b;
                targetBlockIndex ++;
            }
        }

        return new TolLevel(pegData, blockData, targetBlockData);
    }

    public ArrayList<AbstractPeg> getFreePegs(ArrayList<AbstractPeg> level) {
        ArrayList<AbstractPeg> newList = new ArrayList<>();

        for (AbstractPeg p : level) {
            if (p.isFree()) newList.add(p);
        }

        return newList;
    }

    public ArrayList<AbstractPeg> getLoadedPegs(ArrayList<AbstractPeg> level) {
        ArrayList<AbstractPeg> newList = new ArrayList<>();

        for (AbstractPeg p : level) {
            if (p.isLoaded()) newList.add(p);
        }

        return newList;
    }

    public AbstractPeg chooseRandomFreePeg(ArrayList<AbstractPeg> level) {
        Random random = new Random();
        ArrayList<AbstractPeg> freePegs = getFreePegs(level);
        return freePegs.get(random.nextInt(freePegs.size()));
    }

    public AbstractPeg chooseRandomLoadedPeg(ArrayList<AbstractPeg> level) {
        Random random = new Random();
        ArrayList<AbstractPeg> loadedPegs = getLoadedPegs(level);
        return loadedPegs.get(random.nextInt(loadedPegs.size()));
    }
}
