package uk.ac.ox.ndcn.paths.Util;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.util.Pair;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * Created by appdev on 04/04/2016.
 */

public class BlueNoise2D {
    //Grid Jitter  - TODO try poisson disk sampling
    public static class Overflow extends Exception{};
    int numrows, numcols;
    float cellwidth;
    Queue<PointF> unused;
    Random r = new Random();

    public BlueNoise2D(float _width, float _height, float _cellwidth){
        cellwidth = _cellwidth;
        Log.d("dims", _width + " " + _height);
        numrows = (int)Math.floor(_height/cellwidth);
        numcols = (int)Math.floor(_width/cellwidth);
        unused = new PriorityQueue<PointF>(numcols*numrows, new Comparator<PointF>() {
            public int compare(PointF p1, PointF p2) {
                return r.nextInt(2) * 2 - 1;
            }
        });

        for (int x = 0; x<numcols; x++){
            for (int y = 0; y<numrows; y++){
                unused.add(new PointF(x * cellwidth, y * cellwidth));

            }
        }
    }

    public PointF getNextPoint() throws Overflow {
        if(unused.isEmpty()){
            throw new Overflow();
        }
        else {
            return unused.remove();
        }
    }

    public PointF getNextPointWithJitter(float j) throws Overflow {
        PointF p = getNextPoint();
        p.offset(r.nextFloat()* j, r.nextFloat()*j);
        Log.d("x", p.x + " " + p.y);

        return p;
    }

}
