package uk.ac.ox.ndcn.paths.DraggingEntities;

import android.util.Log;
import android.view.MotionEvent;

import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;

/**
 * Created by appdev on 19/06/2016.
 */
public class Draggable extends Entity {
    World world;
    public Draggable(World _world){
        world = _world;
    }

    private boolean dragging = false;
    public void touch (MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if(collidePoint(x, y)) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dragging = true;
                drag_start(x, y);
            }
        }
        if(dragging){

            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    drag_move(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    drag_end();
                    Log.d("++++++++++++++++", "+++++++++++++++");
                    dragging = false;
                    break;
            }

        }
        Log.d("event", ""+ event.getAction());
        Log.d("drag", "" + dragging);
    }

    @Override public void update(){
        /*if(mX != x || mY != y){
            drag_move();
        }*/
    }

    private float mX, mY;
    protected float sX, sY;
    private float oX, oY;
    protected float MAXDRAG = 100;

    protected void drag_start(float x, float y){
        mX = x;
        mY = y;
        sX = this.x;
        sY = this.y;
        oX = sX-mX;
        oY = sY-mY;
    }
    protected boolean drag_collide(float newX, float newY, Entity e){
        return false;
    }
    private void drag_move(float x, float y){

        float dx = x - this.x+oX;
        float dy = y - this.y+oY;

        boolean collidesXY = false;
        boolean collidesX = false;
        boolean collidesY = false;



        for (Entity entity : world.entities) {
            if (drag_collide(this.x+dx , this.y+dy , entity)) {
                if (entity.collisionType == collisionType.OBSTACLE || entity.collisionType == collisionType.PEG) {

                    if (entity != this) {
                        collidesXY = true;
                    }
                }
            }

        }
        ;

        if (collidesXY) {
            //Constrain X seperate to Y

            //X
            for (Entity entity : world.entities) {
                if (drag_collide(this.x+dx , this.y , entity)) {
                    if (entity.collisionType == collisionType.OBSTACLE || entity.collisionType == collisionType.PEG) {

                        if (entity != this) {
                            collidesX = true;
                        }
                    }
                }

            }
            ;
            if(!collidesX){
                this.x += dx;
            }


            //Y
            for (Entity entity : world.entities) {
                if (drag_collide(this.x, this.y + dy, entity)) {
                    if (entity.collisionType == collisionType.OBSTACLE || entity.collisionType == collisionType.PEG) {

                        if (entity != this) {
                            collidesY = true;
                        }
                    }
                }

            }
            ;
            if(!collidesY){
                this.y += dy;
            }
        }
        else{
            // Move normally
            this.x += dx;
            this.y += dy;
        }


    }

    protected void drag_end(){

    }

}
