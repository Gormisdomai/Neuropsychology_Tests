package uk.ac.ox.ndcn.paths.GeneralEntities;

import android.graphics.Canvas;

/**
 * Created by appdev on 22/01/2017.
 */
public class Timer extends TextBox {
    private long end;
    public boolean running = false;
    public Timer(float x, float y){
        super(x, y, "");
        super.y += ((paint.descent() + paint.ascent()) / 2);

    }
    public void start(long target){
        running = true;
        end = System.currentTimeMillis() + target;
    }
    public void stop(){
        running = false;
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (!running) return;
        super.text = "" + (end - System.currentTimeMillis())/1000;
    }
}
