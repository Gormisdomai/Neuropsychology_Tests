package uk.ac.ox.ndcn.paths.GeneralEntities;

import android.app.Activity;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;

import com.dropbox.client2.DropboxAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;

/**
 * Created by appdev on 20/08/15.
 */
public abstract class World extends View{
    public static final String GAMEID = null;

    public ArrayList<Entity> entities = new ArrayList<Entity>();

    public String user = "";

    public Activity a;

    public World(Activity context){
        super(context);
        a = context;
    }
    public World(Activity context, String _user){
        super(context);
        a = context;
        user = _user;
        setWillNotDraw(false);
        this.postInvalidate();
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        updateLogic();

            for (Entity entity : entities) {

                entity.update();
                entity.draw(canvas);

                /*TODO: See Below
                Entities should not be responsible for their own drawing. Each entity should encuapsulate the minimum state required
                to be drawable (graphic, x, y) and rendering to the canvas should be entirely handled by the view.

                Until entities having their own draw() function becomes a problem we will achoeve this seperation of draw()
                and update logic by putting them in different functions.
                */


            }
        this.postInvalidate();
    }

    public ArrayList<Entity> addBuffer = new ArrayList<Entity>();
    public ArrayList<Entity> removeBuffer = new ArrayList<Entity>();
    protected void updateLogic(){
        entities.removeAll(removeBuffer);
        removeBuffer.clear();
        entities.addAll(addBuffer);
        addBuffer.clear();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Iterator<Entity> i = new ArrayList<>(entities).iterator();
        while (i.hasNext()){
            i.next().touch(event);
        }
        invalidate();
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h, oldw,oldh);
        removeAll(entities);
        init(w, h);
    }

    public void init(int w, int h){

    }

    public Entity add(Entity entity) {
        addBuffer.add(entity);
        return entity;
    }

    public Entity remove(Entity entity){
        removeBuffer.add(entity);
        return entity;
    }
    public void removeAll(Collection<Entity> c){
       removeBuffer.addAll(c);
    }
    public void finish(){
        a.finish();
    }
}
