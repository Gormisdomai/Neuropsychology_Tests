package uk.ac.ox.ndcn.paths.GeneralEntities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import uk.ac.ox.ndcn.paths.Util.CollisionFunctions;

/**
 * Created by appdev on 27/03/2016.
 */
public class Circle extends Entity {

    public float radius = 50;
    public Paint paint;

    public Circle(float _x, float _y, float _radius){
        x = _x;
        y = _y;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        radius = _radius;
    }

    public void draw(Canvas canvas){

        canvas.drawCircle(x, y, radius, paint);
    }

    public boolean collidePoint(float x, float y){
        return CollisionFunctions.CircleWithPoint(x, y, this.x, this.y, this.radius);


    }

    public boolean collideLine(float x1, float y1, float x2, float y2){
        return CollisionFunctions.CircleWithLine(x1, y1, x2, y2, x, y, radius);
    }

}
