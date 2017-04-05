package uk.ac.ox.ndcn.paths.MazeEntities;


import java.util.Iterator;

import android.graphics.Canvas;

import uk.ac.ox.ndcn.paths.GeneralEntities.World;

/**
 * Created by appdev on 21/11/15.
 */
public class ObstacleSpawner implements GoalBehaviour {

    Iterator<Obstacle> obstacles;
    //public ArrayList<Obstacle> drawableObstacles = new ArrayList<Obstacle>();
    private World w;

    public ObstacleSpawner(Iterable<Obstacle> os, World _w){
        obstacles = os.iterator();
        w = _w;
    }


    public void run(){
        if (obstacles.hasNext())
        {
            Obstacle o = obstacles.next();
            w.add(o);
            //drawableObstacles.add(o);


        }
    }

    public void draw(Canvas c)
    {
       // for (Obstacle obstacle : drawableObstacles)
        //{
         //   obstacle.draw(c);
        //}
    }
}
