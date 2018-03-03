package uk.ac.ox.ndcn.paths.TowerOfLondonEntities;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import uk.ac.ox.ndcn.paths.Games.TolView;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.Loggers.Logger;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;

import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;
import uk.ac.ox.ndcn.paths.Loggers.ToLLogger;
import uk.ac.ox.ndcn.paths.Util.CollisionFunctions;

/**
 * Created by appdev on 07/08/2016.
 */
public class TowerBlock extends Draggable {
    Paint paint = new Paint();
    protected float width = 150;
    protected float thirdWidth = width/3;
    protected float height = 150;
    private Rect rect;
    protected int color;
    private ToLLogger log;


    public TowerBlock(World _world, float _x, float _y, float _width, float _height, int color, ToLLogger log){
        super(_world);
        this.log = log;
        x = _x;
        y = _y;
        sX = x;
        sY = y;
        width = _width;
        thirdWidth = width/3;
        height = _height;
        rect = new Rect(Math.round(x), Math.round(x+width), Math.round(y), Math.round(y+height));
        paint.setColor(color);
        this.color = color;
        paint.setStrokeWidth(3);
        this.MAXDRAG = Math.min(width/3, height) -1;
        collisionType = CollisionType.OBSTACLE;
    }

    @Override protected boolean drag_collide(float newX, float newY, Entity e){
        //Collision Code Explained
        /*

        *                *    *                *
         |^^^^^^^^^^^^^^|      |^^^^^^^^^^^^^^|
         |              |      |              |  entity approximated as two rectangles = 8 corners
         |______________|      |______________|     to move the entity, first check if any of these corners
        *                *    *                *    would trace a path which collides with another entity


         */


       boolean collides=
                e.collideLine(x, y, newX, newY) ||
                e.collideLine(x + thirdWidth, y, newX+thirdWidth, newY) ||
                e.collideLine(x, y+height, newX, newY+height) ||
                e.collideLine(x + thirdWidth, y+height, newX+thirdWidth, newY+height) ||

                e.collideLine(2*thirdWidth + x, y, 2*thirdWidth + newX, newY) ||
                e.collideLine(2*thirdWidth + x + thirdWidth, y, 2*thirdWidth + newX+thirdWidth, newY) ||
                e.collideLine(2*thirdWidth + x, y+height, 2*thirdWidth + newX, newY+height) ||
                e.collideLine(2*thirdWidth + x + thirdWidth, y+height, 2*thirdWidth + newX+thirdWidth, newY+height)
                ;
        return  collides;
    }
    boolean firstdraw = true;
    public void draw(Canvas canvas){
        if (firstdraw){
            firstdraw = false;
            snap();
        }

        canvas.drawRect(x, y, x+width, y+height, paint);
    }

    public boolean collidePoint(float x, float y){
        return CollisionFunctions.RectWithPoint(x, y, this.x, this.y, this.width, this.height);
    }



    public boolean collideLine(float lineX1, float lineY1, float lineX2, float lineY2) {
        return CollisionFunctions.RectWithLine(lineX1, lineY1, lineX2, lineY2, x, y, width, height);
    }

    @Override protected void drag_start(float x, float y){
        super.drag_start(x, y);
        ((TolView)world).drag_lock = true;

        //TODO why isn't this working?
        for (Entity entity : world.entities) {
            if (entity.collideLine(this.x, this.y, this.x + width -1, this.y + height-1)) {
                if (entity.collisionType == collisionType.PEG) {
                    log.startMove(entity.toString());
                    break;
                }
            }
        }

    }
    @Override protected void drag_end(){
        ((TolView)world).drag_lock = false;
        for (Entity entity : world.entities) {
            if (entity.collideLine(x,y+height,x+width,y+height)) {
                if (entity.collisionType == collisionType.PEG){
                    log.endMove(entity.toString());
                    //TODO CONSTRAIN X SEPERATE TO Y
                    if(entity!=this) {
                        boolean downcollide = false;

                        while (!downcollide){
                            for (Entity e2 : world.entities) {
                                if (e2 != this && e2 != entity) {
                                    if (drag_collide(x, y + 1, e2)) {
                                        if(e2.collisionType != collisionType.PEG)
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
        log.cancelMove();
        x = sX; y = sY;
    }

    public void snap() {
        boolean oldState = log.enabled;
        log.enabled = false;
        drag_end();
        log.enabled = oldState;
    };

    @Override public boolean equals(Object t){
       if (t instanceof TowerBlock)
            return ((TowerBlock) t).color == color;
        else return false;
    }
    @Override public int hashCode(){
        return color;
    }
}
