package uk.ac.ox.ndcn.paths.ButtonsAndKeyPads;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;

/**
 * Created by appdev on 21/08/2016.
 */
public class Button extends Entity {

    float x, y;
    float width, height;
    Paint paint = new Paint();
    TextBox text;

    public Button(float _x, float _y, float _width, float _height, String _text) {
        x = _x;
        y = _y;
        width = _width;
        height = _height;
        text = new TextBox(x+width/2, y + height / 2, _text);

    }

    @Override
    public void  draw(Canvas canvas){
        if(down == true){
            paint.setColor(Color.LTGRAY); //TODO Change to light gray
        }
        else
        {
            paint.setColor(Color.GRAY);
        }
        paint.setStrokeWidth(3);
        canvas.drawRect(x, y, x + width, y + height, paint);
        text.draw(canvas);

    }

    protected void clicked(){

    }
    protected void pushed(){

    }
    protected void released(){

    }
    private boolean down = false;
    public void touch(MotionEvent event) {
        float _x = event.getX();
        float _y = event.getY();
        if (_x > x && _x < x + width && _y > y && _y < y + height) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    down = true;
                    pushed();
                    break;
                case MotionEvent.ACTION_UP:
                    if (down == true) {
                        down = false;
                        clicked();
                    }
                    released();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    down = false;
                    break;

            }
        }
        else {
            down=false;
        }
    }
}
