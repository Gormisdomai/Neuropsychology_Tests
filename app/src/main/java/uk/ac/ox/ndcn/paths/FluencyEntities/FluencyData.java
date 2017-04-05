package uk.ac.ox.ndcn.paths.FluencyEntities;

import java.util.ArrayList;

import uk.ac.ox.ndcn.paths.MazeEntities.PathData;
import uk.ac.ox.ndcn.paths.MazeEntities.timePoint;

/**
 * Created by appdev on 29/05/2016.
 */
public class FluencyData extends PathData {
    public String goal = null;
    public ArrayList<String> goals;

    public FluencyData() {
        data = new ArrayList<timePoint>();
        goals = new ArrayList<String>();
    }

    public FluencyData(FluencyData src) {
        data = new ArrayList<timePoint>(src.data);
        goals = new ArrayList<String>(src.goals);
        start = src.start;
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

    @Override
    public String extraData(){
        return goals.toString();
    }

    public void reset()
    {
        super.reset();
        goals.clear();
        goal = null;
    }
}
