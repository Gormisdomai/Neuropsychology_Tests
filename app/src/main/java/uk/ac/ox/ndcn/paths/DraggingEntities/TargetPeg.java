package uk.ac.ox.ndcn.paths.DraggingEntities;

import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;

/**
 * Created by appdev on 11/01/2017.
 */
public class TargetPeg extends Peg {
    public TargetPeg(float _x, float _y, float _width, float _height, World _world) {
        super(_x, _y, _width, _height, _world);
        collisionType = CollisionType.FAKEPEG;
    }

}
