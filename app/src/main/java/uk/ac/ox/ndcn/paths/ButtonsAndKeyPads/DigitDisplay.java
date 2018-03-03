package uk.ac.ox.ndcn.paths.ButtonsAndKeyPads;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.DoneHandler;

/**
 * Created by appdev on 31/12/2015.
 */
public class DigitDisplay extends Entity {
    Paint paint = new Paint();
    private float width = 150;
    private float height = 150;
    private Rect rect;
    private TextBox t;
    private int[] digits;
    private long startTime;
    private long millisPerDigit;
    private DoneHandler h;

    public DigitDisplay(float _x, float _y, float _width, float _height, int[] _digits, long _millisPerDigit, DoneHandler _h) {
        //x = _x - _width/2;
        //y = _y - _height/2;
        //todo add finished handler
        x = _x;
        y = _y;
        width = _width;
        height = _height;
        rect = new Rect(Math.round(x), Math.round(x + width), Math.round(y), Math.round(y + height));
        digits = _digits;
        t = new TextBox(width/2, height/2, String.format("%d", digits[0]));
        startTime = System.currentTimeMillis();
        millisPerDigit = _millisPerDigit;
        h = _h;
    }

    public void draw(Canvas canvas) {
        long index = (System.currentTimeMillis() - startTime) / millisPerDigit;

        if (index >= digits.length){
            h.done("");
        }
        else
        {
            paint.setColor(Color.DKGRAY);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(x, y, x + width, y + height, paint);
            t.text = String.format("%d", digits[(int)index]);
            t.draw(canvas);
        }
    }

}
