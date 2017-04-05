package uk.ac.ox.ndcn.paths.DraggingEntities;

import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.MazeEntities.Obstacle;

/**
 * Created by appdev on 11/01/2017.
 */
public class TargetObstacle extends Obstacle {
    public TargetObstacle(float _x, float _y, float _width, float _height) {
        super(_x, _y, _width, _height);
        collisionType = CollisionType.BACKGROUND;
    }

}
