package uk.ac.ox.ndcn.paths.Games;

/**
 * Created by appdev on 27/03/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;

import java.util.Arrays;
import java.util.LinkedList;

import uk.ac.ox.ndcn.paths.ButtonsAndKeyPads.Button;
import uk.ac.ox.ndcn.paths.ButtonsAndKeyPads.Noisepad;
import uk.ac.ox.ndcn.paths.DraggingEntities.TowerBlock;
import uk.ac.ox.ndcn.paths.ButtonsAndKeyPads.Keypad;
import uk.ac.ox.ndcn.paths.ButtonsAndKeyPads.DigitDisplay;
import uk.ac.ox.ndcn.paths.GeneralEntities.DoneHandler;
import uk.ac.ox.ndcn.paths.GeneralEntities.InputBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.MazeEntities.Obstacle;

public class NumericalDigitSpanView extends World {


    public int w;
    public int h;
    public static final String GAMEID = "NumericalDigitSpanView";
    public Keypad k;
    public LinkedList<Integer> target = new LinkedList<Integer>();

    public NumericalDigitSpanView(Activity context, String _user, DropboxAPI mDBApi) {
        super(context, _user, mDBApi);

    }
    @Override
    public void init (int _w, int _h) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        w = _w;
        h = _h;
        int [] s = {1, 2, 3};

        for (int index = 0; index < s.length; index++)
        {
            target.add(s[index]);
        }
        k = (Keypad)add(new Keypad(9,false,0,h/3,w, h/2,this));

        add(new DigitDisplay(0, 0, w, h, s, 1000, new DoneHandler() {
            public void done() {
            }
        }));


    }
    @Override
    public void draw(Canvas c){
        super.draw(c);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);
        if(k.input.equals(target)){
            c.drawCircle(0,0,100, paint);
            //finish();
        };
    }


}


