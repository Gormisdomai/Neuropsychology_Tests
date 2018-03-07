package uk.ac.ox.ndcn.paths.MazeEntities;

import android.graphics.Canvas;

import uk.ac.ox.ndcn.paths.GeneralEntities.Circle;
import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;

/**
 * Created by appdev on 23/07/15.
 */
public class Goal extends Circle {
    int label;
    private GoalBehaviour behaviour;

    public Goal(float _x, float _y, float _radius){
        super(_x, _y, _radius);


        label = 0;
        collisionType = CollisionType.GOAL;

    }

    public Goal(float _x, float _y, float _radius, GoalBehaviour b, int label){
        super(_x, _y, _radius);
        this.label = label;
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
