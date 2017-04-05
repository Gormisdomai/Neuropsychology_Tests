package uk.ac.ox.ndcn.paths.GeneralEntities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;

/**
 * Created by appdev on 03/12/2015.
 */
public class TextBox extends Entity {
    protected float x;
    protected float y;
    public String text;
    Paint paint = new Paint();
    public TextBox(float x, float y, String text)
    {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    @Override
    public void draw(Canvas canvas){



        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, x, y - ((paint.descent() + paint.ascent()) / 2), paint);
    }
}
