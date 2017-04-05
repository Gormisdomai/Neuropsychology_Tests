package uk.ac.ox.ndcn.paths.GeneralEntities;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;

/**
 * Created by appdev on 31/12/2015.
 */
public class OpacityBox extends Entity {
    Paint paint = new Paint();
    private float width = 150;
    private float height = 150;
    private Rect rect;


    public OpacityBox(float _x, float _y, float _width, float _height) {
            //x = _x - _width/2;
            //y = _y - _height/2;

            x = _x;
            y = _y;
            width = _width;
            height = _height;
            rect = new Rect(Math.round(x), Math.round(x + width), Math.round(y), Math.round(y + height));

        }

        public void draw(Canvas canvas) {

            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(20);
            canvas.drawRect(x, y, x + width, y + height, paint);
        }

}
