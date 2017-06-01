package uk.ac.ox.ndcn.paths.Util;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import uk.ac.ox.ndcn.paths.ButtonsAndKeyPads.Button;
import uk.ac.ox.ndcn.paths.GeneralEntities.DoneHandler;

/**
 * Created by appdev on 01/06/2017.
 */
public class ClickableImage extends Image {
    DoneHandler handler;
    private boolean pressed = false;
    public ClickableImage(float _x, int _y, int _width, int _height, Bitmap _source,  DoneHandler h){
        super (_x, _y,  _width,  _height,  _source);
        handler = h;
    }


    protected void pushed(){

    }
    protected void released(){

    }
    protected void clicked() {
        if(!pressed) {
            handler.done();

            pressed = true;
        }
    }
    private boolean down = false;
    public void touch(MotionEvent event) {
        if (pressed) return;
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
