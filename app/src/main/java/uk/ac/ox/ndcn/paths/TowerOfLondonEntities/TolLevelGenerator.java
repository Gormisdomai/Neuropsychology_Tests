package uk.ac.ox.ndcn.paths.TowerOfLondonEntities;

import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
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

        @Override
        public boolean equals(Object o) {
            if (o instanceof AbstractPeg) {
                return Arrays.equals(((AbstractPeg) o).blocks.toArray(), blocks.toArray());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString(){
            String r = "";
            for (Float i : blocks){
                r = r + " " + colourize(i);
            }
            return r;
        }

        public String colourize(Float f){
            if (f == -65536.0){
                return "R";
            } else if (f == -1.6776961E7 ) {
                return "B";
            } else {
                return "G";
            }
        }

    }

    private class State {
        public ArrayList<AbstractPeg> pegs = new ArrayList<>();
        public Float lastMovedBlock = null;
        public State lastState = null;
        public int distanceFromStart = 0;

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
                    if (src.blocks.peek() == lastMovedBlock) continue; //don't move the same block twice in a row
                    dest.blocks.push(src.blocks.pop());
                    s.lastMovedBlock = dest.blocks.peek();
                    s.lastState = this;
                    s.distanceFromStart = this.distanceFromStart + 1;
                    availableMoves.add(s);
                }
            }
            return availableMoves;
        }

       @Override
        public boolean equals(Object o) {
            if (o instanceof State && (((State) o).pegs.equals(this.pegs))) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public String toString(){
            return this.pegs.toString();
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

    public int shuffleTarget(int difficulty){
        //now is a DFS but some annoying state paths exist
        // options: make it a BFS: rejig datastructures to keep a wavefront and ignore previously visited states
        // stick with DFS and then try to solve with BFS when you find a soloution.
        // hold off on these for the time being and deliver the basic settings changes they wanted.
        Random random = new Random();
        Set<State> visitedStates = new HashSet<>();
        State hardestState = target;
        visitedStates.add(target);
        int loopCatcher = 0;
        while (target.distanceFromStart < difficulty){
            //Log.d("gen: target", target.toString());
            Set<State> movesToConsider = target.availableMoves();
            movesToConsider.removeAll(visitedStates);
            if (movesToConsider.size() == 0) {
                //Log.d("gen: backtracking", "" + target.distanceFromStart);
                //backtrack
                loopCatcher ++;
                if (target.lastState == null || loopCatcher > 1000){
                    target = hardestState;
                    Log.d("gen: incomp difficulty", "" + target.distanceFromStart);
                    return target.distanceFromStart;
                }
                target = target.lastState;
                continue;
            }

            ArrayList<State> availableMoves = new ArrayList<State>(movesToConsider);
            State nextMove = availableMoves.get(random.nextInt(availableMoves.size()));
            visitedStates.add(nextMove);
            target = nextMove;
            if(hardestState.distanceFromStart < target.distanceFromStart) hardestState = target;
        }
        Log.d("gen: comp difficulty", "" + target.distanceFromStart);
        return target.distanceFromStart;
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
