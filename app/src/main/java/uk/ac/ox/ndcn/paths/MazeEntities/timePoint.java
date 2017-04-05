package uk.ac.ox.ndcn.paths.MazeEntities;

import android.graphics.Point;

/**
 * Created by appdev on 25/09/15.
 */
public class timePoint extends Point {
    public long time;
    public timePoint(int _x, int _y, long _time)
    {
        super(_x, _y);
        time = _time;
    }

    @Override
    public String toString() {
        return "timePoint{ x=" + x + ", y=" + y +
                ", time=" + time +
                '}';
    }
}
