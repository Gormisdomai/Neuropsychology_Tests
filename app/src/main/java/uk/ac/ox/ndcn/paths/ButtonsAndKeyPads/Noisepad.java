package uk.ac.ox.ndcn.paths.ButtonsAndKeyPads;

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
public class Noisepad extends Entity {

    private LinkedList<Key> keys = new LinkedList<Key>();
    public LinkedList<Integer> input = new LinkedList<Integer>();
    public LinkedList<Integer> target = new LinkedList<Integer>();



    private class Key extends Button implements idable {
        private Paint altPaint = new Paint();
        public int id;
        public Key(float _x, float _y, float _radius, int _id){
            super(_x, _y, _radius, _radius, "");
            collisionType = CollisionType.TRAILGOAL;
            id = _id;

            altPaint.setColor(Color.WHITE);
        }

        @Override
        public String getId() {
            return Integer.toString(id);
        }

        @Override
        public void draw(Canvas c)
        {
            long e = elapsed();
            if(cueTime * cueLength > e){
                if(target.get((int)(e / cueTime)) == id){
                    c.drawRect(super.x, super.y, super.x + super.width, super.y + super.height, altPaint);
                }

            }
            else {
                super.draw(c);
            }

        }

        public void clicked(){
            long e = elapsed();
            if(cueTime * cueLength < e)
                input.add(id);
        }
    }

    public float SPACING = 5;
    private int length;
    private int cueLength;
    private float x, y, width, height;
    private boolean usesLetters;
    private World world;
    private long cueTime;
    public Noisepad(int _length, boolean _usesLetters, float _x, float _y, float _width, float _height, World _world, long _cueTime){

        length = _length;
        usesLetters = _usesLetters;
        x = _x;
        y = _y;
        width = _width;
        height = _height;
        world = _world;
        init();
        cueTime = _cueTime;
        cueLength = 3;
        target.add(1);
        target.add(2);
        target.add(3);
    }



    public void init(){
        BlueNoise2D n = new BlueNoise2D(width, height, 300);
        //populate the ArrayList with goals
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(world.getContext());
        int r = Integer.parseInt(prefs.getString("blob_radius", "6"));


        for(int i = 0; i<length; i++){
            try {
                PointF p = n.getNextPointWithJitter(200);
                Key g = new Key(p.x+50,p.y+59,r*10,i);
                keys.add((Key)world.add(g));
            }
            catch (BlueNoise2D.Overflow e)
            {

                Log.d("Trail Drawing", "out of room!");
            }

        }
    }
    long start = System.currentTimeMillis();
    public long elapsed(){
        return System.currentTimeMillis() - start;
    }



}

