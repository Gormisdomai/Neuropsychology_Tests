package uk.ac.ox.ndcn.paths.ComplexFigureEntities;


import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;


import uk.ac.ox.ndcn.paths.GeneralEntities.DoneHandler;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.MazeEntities.OldLines;
import uk.ac.ox.ndcn.paths.MazeEntities.PathData;


/**
 * Created by appdev on 23/07/15.
 */
public class CanvasLine extends Entity implements DoneHandler {
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver() {
        history.add(new Path(path), new PathData(points));
        Log.d("gorm", points.toString());
        Log.d("histgorm", history.pathDatas.toString());
        Log.d("history:", history.toString());
        this.gameOver = true;
        history.save(world.getContext());


        path.reset();
        points.reset();
    }
    public void done(String s){
        setGameOver();
    }

    private boolean gameOver = false;
    Paint line = new Paint();
    Path path = new Path();
    PathData points = new PathData();
    World world;
    OldLines history;
    public long start = -1;


    private boolean dead = false;
    private boolean win = false;



    public CanvasLine(World _world, OldLines _history) {
        world = _world;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(world.getContext());
        line.setAntiAlias(true);
        line.setDither(true);
        line.setColor(Color.BLACK);
        line.setStyle(Paint.Style.STROKE);
        line.setStrokeJoin(Paint.Join.ROUND);
        line.setStrokeCap(Paint.Cap.ROUND);
        line.setStrokeWidth(Integer.parseInt(prefs.getString("line_width", "6")));


        history = _history;

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
    private boolean firstouch =true;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 1;

    private void touch_start(float x, float y) {
        if (start == -1){
            start = System.currentTimeMillis();
        }
        if (firstouch){
            mX = x; mY = y;

            firstouch = false;
        }

        dead = false;
        win = false;
        line.setColor(Color.BLACK);
        points.start = (System.currentTimeMillis());
        path.reset();
        points.reset();
        path.moveTo(x, y);
        points.add(x, y,System.currentTimeMillis());
        mX = x;
        mY = y;
        //dead = true;
        //points.end = System.currentTimeMillis();

    }
    private void touch_move(float x, float y) {
        if (firstouch){
            mX = x; mY = y;
            path.moveTo(x, y);
            firstouch = false;
        }
        if (!(dead || win)) {
            Log.d("", ""+mX);
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
        if (firstouch){
            mX = x; mY = y;
            path.moveTo(x, y);
            firstouch = false;
        }
        win = true; // draw all lines
        if (win && !dead) {
            path.lineTo(mX, mY);
            history.add(new Path(path), new PathData(points));
            Log.d("gorm", points.toString());
        }
    }


}
