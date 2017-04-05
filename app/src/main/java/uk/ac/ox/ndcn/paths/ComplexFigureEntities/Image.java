package uk.ac.ox.ndcn.paths.ComplexFigureEntities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.graphics.Rect;

import uk.ac.ox.ndcn.paths.GeneralEntities.DoneHandler;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;

/**
 * Created by appdev on 25/04/2016.
 */
public class Image extends Entity implements DoneHandler {
    public Bitmap source;
    public Paint paint;
    int width, height;
    public Boolean visible = true;

    public void done() {
        visible = false;
    }

    public Image(float _x, int _y, int _width, int _height, Bitmap _source){
        x = _x;
        y = _y;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        width = _width;
        height = _height;
        source = _source;
    }

    public void draw(Canvas canvas){
        if (visible) {
            Rect src = new Rect(0, 0, source.getWidth() - 1, source.getHeight() - 1);
            double scale = Math.min(((float) width) / source.getWidth(), ((float) height) / source.getHeight());
            int w = (int) Math.floor(scale * source.getWidth());
            int h = (int) Math.floor(scale * source.getHeight());
            Rect dest = new Rect((int)x, (int)y, (int)x + w - 1, (int) y + h - 1);

            Log.d("draw", width / source.getWidth() + "," + height / source.getHeight() + "," + scale);

            canvas.drawBitmap(source, src, dest, paint);
        }
    }
}
