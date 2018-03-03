package uk.ac.ox.ndcn.paths.MazeEntities;


import java.util.Iterator;

import android.graphics.Canvas;

import uk.ac.ox.ndcn.paths.Games.SwitchPathsView;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;

/**
 * Created by appdev on 21/11/15.
 */
public class ObstacleSpawner implements GoalBehaviour {

    Iterator<Obstacle> obstacles;
    //public ArrayList<Obstacle> drawableObstacles = new ArrayList<Obstacle>();
    private SwitchPathsView w;
    private int scoreIncrement;

    public ObstacleSpawner(Iterable<Obstacle> os, SwitchPathsView _w, int _scoreIncrement){
        obstacles = os.iterator();
        w = _w;
        scoreIncrement = _scoreIncrement;
    }


    public void run(){
        if (obstacles.hasNext())
        {
            Obstacle o = obstacles.next();
            w.add(o);
            w.score += scoreIncrement;
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
