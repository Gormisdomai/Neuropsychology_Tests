package uk.ac.ox.ndcn.paths.MazeEntities;

import android.graphics.Canvas;
import android.graphics.Paint;

import uk.ac.ox.ndcn.paths.GeneralEntities.Circle;
import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import android.util.Log;

/**
 * Created by appdev on 23/07/15.
 */
public class Goal extends Circle {
    static int idCount = 0;
    int id;
    private GoalBehaviour behaviour;

    public Goal(float _x, float _y, float _radius){
        super(_x, _y, _radius);


        id = idCount;
        idCount += 1;
        collisionType = CollisionType.GOAL;

    }

    public Goal(float _x, float _y, float _radius, GoalBehaviour b){
        super(_x, _y, _radius);
        id = idCount;
        idCount += 1;
        collisionType = CollisionType.GOAL;
        behaviour = b;

    }
    @Override
    public void handleCollision(Entity e){
        if (behaviour != null){
            behaviour.run();
        }
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(x, y, radius, paint);
        if (behaviour != null){
            behaviour.draw(canvas);
        }
    }


}
