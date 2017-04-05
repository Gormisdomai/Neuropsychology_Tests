package uk.ac.ox.ndcn.paths.MazeEntities;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.Util.CollisionFunctions;

/**
 * Created by appdev on 23/07/15.
 */
public class Obstacle extends Entity {
    Paint paint = new Paint();
    protected float width = 150;
    protected float height = 150;
    private Rect rect;


    public Obstacle(float _x, float _y, float _width, float _height){
        //x = _x - _width/2;
        //y = _y - _height/2;
        x = _x;
        y = _y;
        width = _width;
        height = _height;
        rect = new Rect(Math.round(x), Math.round(x+width), Math.round(y), Math.round(y+height));

        collisionType = CollisionType.OBSTACLE;
    }

    public void draw(Canvas canvas){

        paint.setColor(Color.LTGRAY);
        paint.setStrokeWidth(3);
        canvas.drawRect(x, y, x+width, y+height, paint);
    }

    public boolean collidePoint(float x, float y){
        return CollisionFunctions.RectWithPoint(x, y, this.x, this.y, this.width, this.height);
    }


    public boolean collideLine(float lineX1, float lineY1, float lineX2, float lineY2) {
        return CollisionFunctions.RectWithLine(lineX1,lineY1,lineX2,lineY2,x,y,width,height);
    }


}
