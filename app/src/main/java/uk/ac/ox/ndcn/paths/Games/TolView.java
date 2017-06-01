package uk.ac.ox.ndcn.paths.Games;

/**
 * Created by appdev on 27/03/2016.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;

import uk.ac.ox.ndcn.paths.ComplexFigureEntities.DoneButton;

import com.dropbox.client2.DropboxAPI;

import uk.ac.ox.ndcn.paths.DraggingEntities.TargetPeg;
import uk.ac.ox.ndcn.paths.DraggingEntities.TolLevel;
import uk.ac.ox.ndcn.paths.DraggingEntities.Peg;
import uk.ac.ox.ndcn.paths.GeneralEntities.DoneHandler;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;

import java.util.ArrayList;

import uk.ac.ox.ndcn.paths.Util.Image;

public class TolView extends World implements DoneHandler {

    private int state = 0;
    public int w;
    public int h;
    public static final String GAMEID = "TolView";
    public boolean drag_lock = false;

    public ArrayList<Peg> pegs = new ArrayList<>();
    public ArrayList<TargetPeg> targetPegs = new ArrayList<>();


    Image target;


    public TolView(Activity context, String _user, DropboxAPI mDBApi) {
        super(context, _user, mDBApi);


    }
    private boolean inited = false;
    @Override
    public void init (int _w, int _h) {
        inited = true;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        w = _w;
        h = _h;
        float [][] pegData = {
                {1f/4,3},
                {1f/2,1},
                {3f/4,4},
        };
        float [][] blockData = {
                {3f/4, 1, Color.RED},
                {1f/2, 1, Color.BLUE},
                {1f/4, 1, Color.GREEN},

        };
        float [][] targetblockData = {
                {3f/4, 1, Color.GREEN},
                {1f/2, 1, Color.BLUE},
                {1f/4, 1, Color.RED}

        };
        (new TolLevel(pegData, blockData, targetblockData)).build(this);
        add(new DoneButton(0, 0, Math.max(w / 7, 100), h/16, this));

    }

    @Override
    public void draw(Canvas c){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);
        super.draw(c);
        if (!inited) return;
        if (pegs.equals(targetPegs)){
            c.drawCircle(0, 0, 100, paint);

        }



    }

    public void done(){
        nextState();
    }

    public void updateLogic(){
        super.updateLogic();
    }

    public void nextState(){
        state ++;
        switch (state){
            case 1:
                removeAll(entities);
                float [][] pegData = {
                        {1f/4,3},
                        {1f/2,1},
                        {3f/4,4},
                };
                float [][] blockData = {
                        {3f/4, 3, Color.GREEN},
                        {3f/4, 2, Color.BLUE},
                        {3f/4, 1, Color.RED},

                };
                float [][] targetblockData = {
                        {3f/4, 1, Color.GREEN},
                        {3f/4, 2, Color.BLUE},
                        {3f/4, 3, Color.RED}

                };
                (new TolLevel(pegData, blockData, targetblockData)).build(this);
                add(new DoneButton(0, 0, Math.max(w / 7, 100), h / 16, this));
                break;
        }
    }


}
