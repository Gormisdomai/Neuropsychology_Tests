package uk.ac.ox.ndcn.paths.TrailEntities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import uk.ac.ox.ndcn.paths.GeneralEntities.Circle;
import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.Util.BlueNoise2D;
import uk.ac.ox.ndcn.paths.Util.idable;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by appdev on 27/03/2016.
 */
public class Trail extends Entity {

    private Queue<TrailGoal> goals = new LinkedList<TrailGoal>();
    private ArrayList<TrailGoal> toRemove = new ArrayList<TrailGoal>();
    private class TrailGoal extends Circle implements idable {
        private Paint textPaint = new Paint();
        public String text;
        public TrailGoal(float _x, float _y, float _radius, String _text){
            super(_x, _y, _radius);
            collisionType = CollisionType.TRAILGOAL;
            text = _text;


            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(50);
        }
        @Override
        public String getId(){
            return text;
        }

        public void draw(Canvas c)
        {
            super.draw(c);
            c.drawText(text, x - radius / 2, y + radius / 2, textPaint);
        }

        public void handleCollision(Entity e){
            if (goals.peek() == this || (allowsIllegalMoves && !toRemove.contains(this))) {
                goals.remove(this);
                toRemove.add(this);
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(6);
                paint.setStyle(Paint.Style.STROKE);
                if (goals.size() == 1){
                    goals.peek().collisionType = CollisionType.GOAL;
                }
            }
        }
    }

    public float SPACING = 5;
    private int length;
    private float x, y, width, height;
    private boolean usesLetters;
    private World world;
    private boolean allowsIllegalMoves;
    public Trail(int _length, boolean _usesLetters, boolean _allowsIllegalMoves, float _x, float _y, float _width, float _height, World _world){

        length = _length;
        usesLetters = _usesLetters;
        allowsIllegalMoves = _allowsIllegalMoves;
        x = _x;
        y = _y;
        width = _width;
        height = _height;
        world = _world;
        init();
    }


    public void reset(){
        for (TrailGoal t : toRemove){
            world.remove(t);
        }
        init();
    }

    public void init(){
        BlueNoise2D n = new BlueNoise2D(width, height, 300);
        //populate the ArrayList with goals
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(world.getContext());
        int r = Integer.parseInt(prefs.getString("blob_radius", "6"));


        for(int i = 0; i<length; i++){
            try {
                PointF p = n.getNextPointWithJitter(200);
                TrailGoal g = new TrailGoal(p.x+50,p.y+59,r*10,getLabel(i));
                goals.add((TrailGoal)world.add(g));
            }
            catch (BlueNoise2D.Overflow e)
            {

                Log.d("Trail Drawing", "out of room!");
            }

        }
    }

    private String getLabel(int i){
        if (usesLetters){
            if (i%2 == 0) return ""+(i/2+1); else return (char)('A' + ((i-1)/2)) + "";
        }
        else
            return i+1+"";
    }

}

