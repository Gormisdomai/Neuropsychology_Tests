package uk.ac.ox.ndcn.paths.TowerOfLondonEntities;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;
import java.util.Set;

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

    private class State {
        public ArrayList<AbstractPeg> pegs = new ArrayList<>();
        public AbstractPeg lastLoadedPeg = null;

        public State (ArrayList<AbstractPeg> pegs){
            for(AbstractPeg p : pegs) {
                this.pegs.add(p.clone());
            }
        }

        public void add (AbstractPeg a){
            pegs.add(a);
        }

        public AbstractPeg chooseRandomFreePeg() {
            Random random = new Random();
            ArrayList<AbstractPeg> freePegs = getFreePegs();
            return freePegs.get(random.nextInt(freePegs.size()));
        }

        public AbstractPeg chooseRandomLoadedPeg() {
            Random random = new Random();
            ArrayList<AbstractPeg> loadedPegs = getLoadedPegs();
            return loadedPegs.get(random.nextInt(loadedPegs.size()));
        }

        public ArrayList<AbstractPeg> getFreePegs() {
            ArrayList<AbstractPeg> newList = new ArrayList<>();

            for (AbstractPeg p : pegs) {
                if (p.isFree()) newList.add(p);
            }

            return newList;
        }
        public ArrayList<AbstractPeg> getLoadedPegs() {
            ArrayList<AbstractPeg> newList = new ArrayList<>();

            for (AbstractPeg p : pegs) {
                if (p.isLoaded()) newList.add(p);
            }

            return newList;
        }

        public Set<State> availableMoves(){
            Set availableMoves = new HashSet();
            for (int i = 0; i<getLoadedPegs().size(); i++){
                for (int j = 0; j<getFreePegs().size(); j++){
                    State s = new State(pegs);
                    AbstractPeg src = s.getLoadedPegs().get(i);
                    AbstractPeg dest = s.getFreePegs().get(j);
                    if (src == dest) continue; //no need to move to the same peg
                    if (getLoadedPegs().get(i) == lastLoadedPeg) continue; //don't move the same block twice in a row
                    dest.blocks.push(src.blocks.pop());
                    s.lastLoadedPeg = dest;
                    availableMoves.add(s);
                }
            }
            return availableMoves;
        }

        public State without (AbstractPeg p) {
            if (p == null){
                return new State(pegs);
            }
            ArrayList<AbstractPeg> r = ((ArrayList<AbstractPeg>) pegs.clone());
            r.remove(p);
            return new State(r);
        }
    }

    State level = new State (new ArrayList<AbstractPeg>());
    State target = new State (new ArrayList<AbstractPeg>());
    int numBlocks;

    public TolLevelGenerator(int [] pegHeights, float [] blocks) {

        for(int i = 0; i < pegHeights.length; i ++){
            level.add(new AbstractPeg(pegHeights[i]));
        }

        for(int i = 0; i < blocks.length; i ++){
            level.chooseRandomFreePeg().blocks.push(blocks[i]);
        }

        for(AbstractPeg p : level.pegs) {
            target.add(p.clone());
        }

        numBlocks = blocks.length;
    }

    public void shuffleTarget(int difficulty){
        //at the moment this performs d moves. In future prevent revisiting previously visited states by maintaining a history
        //rejig to generate set of next states - and check if any aren't in history. give up and return if there are none
        //(prevent moving to same peg)
        Random random = new Random();
        Set<State> visitedStates = new HashSet<>();
        visitedStates.add(target);
        for (int i = 0; i < difficulty; i ++){
            Set<State> movesToConsider = target.availableMoves();
            movesToConsider.removeAll(visitedStates);
            if (movesToConsider.size() == 0) {
                Log.d("incomplete difficulty", "" + i);
                return;
            }

            ArrayList<State> availableMoves = new ArrayList<State>(movesToConsider);
            State nextMove = availableMoves.get(random.nextInt(availableMoves.size()));
            visitedStates.add(nextMove);
            target = nextMove;
        }
    }

    public TolLevel ConvertLevel(){
        float [][] pegData = new float[level.pegs.size()][2];
        float [][] blockData = new float[numBlocks][3];
        int pegIndex = 0;
        int blockIndex = 0;
        for (AbstractPeg p : level.pegs) {
            float pegPosition = (pegIndex+1f)/(level.pegs.size()+1f);;
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
        for (AbstractPeg p : target.pegs) {
            float targetPegPosition = (targetPegIndex+1f)/(level.pegs.size()+1f);;
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


}
