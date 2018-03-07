package uk.ac.ox.ndcn.paths.MazeEntities;

import java.util.ArrayList;

/**
 * Created by appdev on 25/09/15.
 */
public class PathData {
    public ArrayList<timePoint> data;
    public long start;
    public long end;
    public int goalData;

    public PathData() {
        data = new ArrayList<timePoint>();
    }

    public PathData (PathData src) {
        data = new ArrayList<timePoint>(src.data);
        start = src.start;
        end = src.end;
        goalData = src.goalData;
    }

    @Override
    public String toString() {
        return "Path{" +
                "Points=" + data + ", " +
                "Goal=" + goalData +
                '}';
    }

    public void reset(){
        data.clear();
    }




    public void add(float x, float y, long time)
    {
        data.add(new timePoint((int) x, (int) y, time));
    }

    public String extraData(){
        return "";
    }

    public void finish(long _end)
    {
        end = _end;
    }


}
