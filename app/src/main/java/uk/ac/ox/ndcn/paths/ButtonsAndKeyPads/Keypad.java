package uk.ac.ox.ndcn.paths.ButtonsAndKeyPads;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;

import java.util.LinkedList;

import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.Util.idable;

/**
 * Created by appdev on 27/03/2016.
 */
public class Keypad extends Entity {

    private Key last;
    private LinkedList<Key> keys = new LinkedList<Key>();
    public LinkedList<Integer> input = new LinkedList<Integer>();

    private class Key extends Button implements idable {
        private Paint textPaint = new Paint();
        public int id;
        public Key(float _x, float _y, float _radius, int _id){
            super(_x+__x, _y+__y, _radius, _radius, Integer.toString(_id));
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

        public void clicked(){
            input.add(id);
        }
    }

    public float SPACING = 5;
    private int length;
    private float __x, __y, width, height;
    private boolean usesLetters;
    private World world;
    public Keypad(int _length, boolean _usesLetters, float _x, float _y, float _width, float _height, World _world){

        length = _length;
        usesLetters = _usesLetters;
        __x = _x;
        __y = _y;
        width = _width;
        height = _height;
        world = _world;
        init();
    }

    public void reset(){
        input.clear();
    }

    public void init(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(world.getContext());
        int r = Integer.parseInt(prefs.getString("blob_radius", "6")) * 20;

        assert(length == 5);
        /*keys.add((TrailGoal)world.add(new TrailGoal(width/2, height/2, r, ""))); //center
        keys.add((TrailGoal)world.add(new TrailGoal(4*width/5, height/2, r, ""))); //right
        keys.add((TrailGoal)world.add(new TrailGoal(width/2, height/5, r, ""))); //top
        keys.add((TrailGoal)world.add(new TrailGoal(width/5, height/2, r, ""))); //bottom right
        keys.add((TrailGoal)world.add(new TrailGoal(width/2, 4*height/5, r, ""))); //bottom left
        */
        keys.add((Key) world.add(new Key(width / 2, height / 2, r, 5))); //center
        keys.add((Key) world.add(new Key(4 * width / 5, height / 2, r, 6))); //center right
        keys.add((Key) world.add(new Key(width / 5, height / 2, r, 4))); //center left
        keys.add((Key) world.add(new Key(width / 2, height / 5, r, 2))); //center top
        keys.add((Key) world.add(new Key(width / 2, 4 * height / 5, r, 8))); //center bottom
        keys.add((Key) world.add(new Key(4 * width / 5, height / 5, r, 3))); //top right
        keys.add((Key) world.add(new Key(width / 5, height / 5, r, 1))); //top left
        keys.add((Key) world.add(new Key(width / 5, 4 * height / 5, r, 7))); //bottom right
        keys.add((Key) world.add(new Key(4 * width / 5, 4 * height / 5, r, 9))); //bottom left

    }

    private String getLabel(int i){
        if (usesLetters){
            if (i%2 == 0) return ""+(i/2+1); else return (char)('A' + ((i-1)/2)) + "";
        }
        else
            return i+1+"";
    }

}

