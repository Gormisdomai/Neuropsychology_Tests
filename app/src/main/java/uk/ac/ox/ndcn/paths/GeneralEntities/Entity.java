package uk.ac.ox.ndcn.paths.GeneralEntities;

/**
 * Created by appdev on 26/07/15.
 */

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

abstract public class Entity {

    public float x, y;


    public void draw(Canvas canvas){

    }

    public void update(){

    }

    public void touch (MotionEvent event) {

    }
    public void handleCollision(Entity e){
        Log.d("Entity", "Collide");
    }

    public boolean collidePoint(float x, float y) {
        return  false;
    }

    public boolean collideLine(float x1, float y1, float x2, float y2) {
        return false;
    }

    public CollisionType collisionType;


}
