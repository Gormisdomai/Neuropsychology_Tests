package uk.ac.ox.ndcn.paths.FluencyEntities;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import uk.ac.ox.ndcn.paths.GeneralEntities.Circle;
import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.Util.idable;

/**
 * Created by appdev on 27/03/2016.
 */
public class Trail extends Entity {

    private TrailGoal last;
    private Queue<TrailGoal> goals = new LinkedList<TrailGoal>();

    private class TrailGoal extends Circle implements idable {
        private Paint textPaint = new Paint();
        public int id;
        private Set<TrailGoal> children = new HashSet<TrailGoal>();
        public TrailGoal(float _x, float _y, float _radius, int _id){
            super(_x, _y, _radius);
            collisionType = CollisionType.TRAILGOAL;
            id = _id;

            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(50);
        }

        @Override
        public String getId() {
            return Integer.toString(id);
        }

        @Override
        public void draw(Canvas c)
        {
            super.draw(c);
        }

        public void handleCollision(Entity e){
            if (last != this && !children.contains(last)) {
                if (last == null){
                    //first point
                    paint.setColor(Color.GREEN);
                    paint.setStrokeWidth(6);
                    paint.setStyle(Paint.Style.STROKE);
                }
                else{
                    //subsequent points
                    paint.setColor(Color.GREEN);
                    paint.setStrokeWidth(6);
                    paint.setStyle(Paint.Style.STROKE);
                    last.children.add(this);
                    children.add(last);
                }
                last = this;
                for (TrailGoal t : goals){
                    if(t == this) continue;
                    if (children.contains(t)){
                        t.paint.setStrokeWidth(6);
                        t.paint.setStyle(Paint.Style.STROKE);
                        t.paint.setColor(Color.RED);
                    }
                    else{
                        t.paint.setStyle(Paint.Style.FILL);
                        t.paint.setColor(Color.RED);
                    }
                }
            }
        }
    }

    public float SPACING = 5;
    private int length;
    private float x, y, width, height;
    private boolean usesLetters;
    private World world;
    public Trail(int _length, boolean _usesLetters, float _x, float _y, float _width, float _height, World _world){

        length = _length;
        usesLetters = _usesLetters;
        x = _x;
        y = _y;
        width = _width;
        height = _height;
        world = _world;
        init();
    }

    public void reset(){
        for(TrailGoal goal: goals){
            goal.paint.setColor(Color.RED);
            goal.paint.setStyle(Paint.Style.FILL);
            goal.children.clear();
            last = null;
        }
    }

    public void init(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(world.getContext());
        int r = Integer.parseInt(prefs.getString("blob_radius", "6")) * 10;

        assert(length == 5);
        /*goals.add((TrailGoal)world.add(new TrailGoal(width/2, height/2, r, ""))); //center
        goals.add((TrailGoal)world.add(new TrailGoal(4*width/5, height/2, r, ""))); //right
        goals.add((TrailGoal)world.add(new TrailGoal(width/2, height/5, r, ""))); //top
        goals.add((TrailGoal)world.add(new TrailGoal(width/5, height/2, r, ""))); //bottom right
        goals.add((TrailGoal)world.add(new TrailGoal(width/2, 4*height/5, r, ""))); //bottom left
        */
        goals.add((TrailGoal)world.add(new TrailGoal(width/2, height/2, r, 0))); //center
        goals.add((TrailGoal)world.add(new TrailGoal(4*width/5, height/5, r, 1))); //top right
        goals.add((TrailGoal)world.add(new TrailGoal(width/5, height/5, r, 2))); //top left
        goals.add((TrailGoal)world.add(new TrailGoal(width/5, 4*height/5, r, 3))); //bottom right
        goals.add((TrailGoal)world.add(new TrailGoal(4*width/5, 4*height/5, r, 4))); //bottom left

    }

    private String getLabel(int i){
        if (usesLetters){
            if (i%2 == 0) return ""+(i/2+1); else return (char)('A' + ((i-1)/2)) + "";
        }
        else
            return i+1+"";
    }

}

