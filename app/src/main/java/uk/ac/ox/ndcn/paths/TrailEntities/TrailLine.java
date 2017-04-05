package uk.ac.ox.ndcn.paths.TrailEntities;


import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;

import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.MazeEntities.OldLines;


/**
 * Created by appdev on 23/07/15.
 */
public class TrailLine extends Entity {
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver() {
        this.gameOver = true;
        history.add(new Path(path), new TrailData(points));
        history.save(world.getContext());
        Log.d("history:", history.toString());

    }

    private boolean gameOver = false;
    Paint line = new Paint();
    Paint greyline = new Paint();
    Path path = new Path();
    TrailData points = new TrailData();
    World world;
    OldLines history;
    public long start = -1;

    Trail t;

    private boolean dead = false;
    private boolean win = false;



    public TrailLine(World _world, OldLines _history, Trail _t) {
        world = _world;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(world.getContext());
        line.setAntiAlias(true);
        line.setDither(true);
        line.setColor(Color.YELLOW);
        line.setStyle(Paint.Style.STROKE);
        line.setStrokeJoin(Paint.Join.ROUND);
        line.setStrokeCap(Paint.Cap.ROUND);
        line.setStrokeWidth(Integer.parseInt(prefs.getString("line_width", "6")));


        greyline.setAntiAlias(true);
        greyline.setDither(true);
        greyline.setColor(Color.GRAY);
        greyline.setStyle(Paint.Style.STROKE);
        greyline.setStrokeJoin(Paint.Join.ROUND);
        greyline.setStrokeCap(Paint.Cap.ROUND);
        greyline.setStrokeWidth(Integer.parseInt(prefs.getString("line_width", "6")));

        history = _history;
        t = _t;

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, line);
    }

    public void touch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (!gameOver)
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                break;
        }
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touch_start(float x, float y) {
        if (start == -1){
            start = System.currentTimeMillis();
        }
        dead = false;
        win = false;
        line.setColor(Color.YELLOW);
        points.start = (System.currentTimeMillis());

        path.reset();
        points.reset();
        path.moveTo(x, y);
        points.add(x, y,System.currentTimeMillis());
        mX = x;
        mY = y;
        return;


    }
    private void touch_move(float x, float y) {
        if (!(dead || win)) {
            for (Entity entity : world.entities) {
                if (entity.collideLine(mX, mY, x, y)) {
                    switch (entity.collisionType) {
                       /* case OBSTACLE:
                            line.setColor(Color.RED);
                            dead = true;
                            points.end = System.currentTimeMillis();
                            break;*/
                        case GOAL:
                            line.setColor(Color.GREEN);
                            win = true;
                            points.end = System.currentTimeMillis();
                            points.addGoal(((uk.ac.ox.ndcn.paths.Util.idable) entity).getId());
                            entity.handleCollision(this);
                            break;
                        case TRAILGOAL:
                            Log.d("Trail Line", "Collide");
                            entity.handleCollision(this);
                            points.addGoal(((uk.ac.ox.ndcn.paths.Util.idable) entity).getId());
                            break;


                    }
                }
            }
            ;

            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if ((dx >= TOUCH_TOLERANCE) || (dy >= TOUCH_TOLERANCE)) {
                path.lineTo(x, y);
                points.add(x, y, System.currentTimeMillis());
                mX = x;
                mY = y;
            }
            // TODO save path and timings
        }

    }
    private void touch_up() {
        if (win && !dead) {
            path.lineTo(mX, mY);
            history.add(new Path(path), new TrailData(points));
            t.reset();
        }
        path.reset();
        points.reset();
    }


}
