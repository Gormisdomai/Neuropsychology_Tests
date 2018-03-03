package uk.ac.ox.ndcn.paths.TowerOfLondonEntities;

import android.view.MotionEvent;

import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.Loggers.Logger;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;

/**
 * Created by appdev on 11/01/2017.
 */
public class TargetBlock extends TowerBlock {
    public TargetBlock(World _world, float _x, float _y, float _width, float _height, int color, Logger log) {
        super(_world, _x, _y, _width, _height, color, log);
        collisionType = CollisionType.OBSTACLE;
    }
    @Override public void touch(MotionEvent e){
        return;
    }
    @Override public void snap(){
        for (Entity entity : world.entities) {
            if (entity.collideLine(x,y+height,x+width,y+height)) {
                if (entity.collisionType == collisionType.FAKEPEG){

                    //TODO CONSTRAIN X SEPERATE TO Y
                    if(entity!=this) {
                        boolean downcollide = false;

                        while (!downcollide){
                            for (Entity e2 : world.entities) {
                                if (e2 != this && e2 != entity) {
                                    if (drag_collide(x, y + 1, e2)) {
                                        if(e2.collisionType != collisionType.FAKEPEG)
                                            downcollide = true;
                                        break;
                                    }
                                }
                            }
                            if (!downcollide) y += 1;
                        }
                        x = entity.x-width/2;

                        return;
                    }

                }
            }

        };
        //reset position
        x = sX; y = sY;
    }

}
