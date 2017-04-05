package uk.ac.ox.ndcn.paths.MazeEntities;

/**
 * Created by appdev on 23/07/15.
 */

import uk.ac.ox.ndcn.paths.GeneralEntities.Circle;
import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;

public class Start extends Circle {


    public Start(float _x, float _y, float _radius){
        super(_x, _y, _radius);
        collisionType = CollisionType.START;

    }
}
