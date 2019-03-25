package uk.ac.ox.ndcn.paths.TrailEntities;

import android.util.Log;

import java.util.ArrayList;

import uk.ac.ox.ndcn.paths.MazeEntities.Goal;
import uk.ac.ox.ndcn.paths.MazeEntities.PathData;
import uk.ac.ox.ndcn.paths.MazeEntities.timePoint;

/**
 * Created by appdev on 29/05/2016.
 */
public class TrailData extends PathData {
    public String goal = null;
    public ArrayList<String> goals;

    public String pairs = "";
    public TrailData() {
        data = new ArrayList<timePoint>();
        goals = new ArrayList<String>();
    }

    public TrailData(TrailData src) {
        data = new ArrayList<timePoint>(src.data);
        goals = new ArrayList<String>(src.goals);
        start = src.start;
        pairs = src.pairs;
        end = src.end;
    }

    @Override
    public String toString() {
        return "Path{" +
                "goals=" + goals + ", " +
                "Points=" + data +
                '}';
    }

    public void addGoal(String g) {
        if (g != goal) {
            goal = g;
            goals.add(goal);
        }

    }

    private String lastPair = "";
    public void addPair(String g, String g2, boolean usesLetters){
        boolean right;
        if (lastPair.equals(g + g2)) return;
        if (g == g2) return;
        if (usesLetters){
            if(Character.isDigit(g.charAt(0))){
                String expected = (char)('A' + (Integer.parseInt(g)-1))+ "";
                right = expected.equals(g2);
            }
            else {
                String expected = (g.charAt(0) - 'A') + 2 + "";
                right = expected.equals(g2);
            }
        }
        else {
            right = Integer.parseInt(g) + 1 == Integer.parseInt(g2);
        }
        pairs = pairs + g + " " + g2 + " " + right + "\n";
        lastPair = g + g2;
    }

    @Override
    public String extraData(){
        return goals.toString() + "\n" + pairs;
    }

    public void reset()
    {
        super.reset();
        goals.clear();
        goal = null;
        pairs = "";
    }
}
