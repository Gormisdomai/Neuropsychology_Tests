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

    protected void drag_start(float x, float y){
        mX = x;
        mY = y;
        sX = this.x;
        sY = this.y;
    }
    protected boolean drag_collide(float newX, float newY, Entity e){
        return false;
    }
    protected float MAXDRAG = 100;
    private void drag_move(float x, float y){

        float dx = x - mX;
        float dy = y - mY;

        if (Math.abs(dx) > MAXDRAG || Math.abs(dy) > MAXDRAG){
            //scale dx & dy appropriately, do not return.
            if(Math.abs(dx) > Math.abs(dy)){
                float scale = Math.abs(dx)/MAXDRAG;
                dx = MAXDRAG * Math.signum(dx);
                dy = dy/scale;
            }
            else{
                float scale = Math.abs(dy)/MAXDRAG;
                dy = MAXDRAG * Math.signum(dy);
                dx = dx/scale;
            }
        }
        boolean collidesXY = false;
            for (Entity entity : world.entities) {
                if (drag_collide(this.x+dx, this.y+dy, entity)) {
                    if (entity.collisionType == collisionType.OBSTACLE || entity.collisionType == collisionType.PEG){

                        //TODO CONSTRAIN X SEPERATE TO Y
                        if(entity!=this) {
                            collidesXY = true;
                        }
                    } 
                }

            };

        if (collidesXY){
            /* TODO choose which of these to do first
            boolean collidesX = false;
            for (Entity entity : world.entities) {
                if (drag_collide(this.x+dx, this.y+dy, entity)) {
                    if (entity.collisionType == collisionType.OBSTACLE){

                        //TODO CONSTRAIN X SEPERATE TO Y
                        collidesX = true;
                    }
                }

            };
            */

        }
        else{
            this.x += dx;
            this.y += dy;



            mX = x;
            mY = y;
        }


    }

    protected void drag_end(){

    }

}
