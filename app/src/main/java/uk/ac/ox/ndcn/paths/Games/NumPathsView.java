package uk.ac.ox.ndcn.paths.Games;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


import com.dropbox.client2.DropboxAPI;

import uk.ac.ox.ndcn.paths.GeneralEntities.OpacityBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.MazeEntities.*;


/**
 * This class implements a canvas "world" onto which we can draw paths
 */
public class NumPathsView extends World {

    public static final String GAMEID = "NUMPATHSVIEW";
    public MazeLine line;
    public int w;
    public int h;
    private int timeout = 240000;

    public NumPathsView(Activity context, String _user, DropboxAPI mDBApi) {
        super(context, _user, mDBApi);



    }


    @Override
    public void init (int w, int h) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int r = Integer.parseInt(prefs.getString("blob_radius", "6"));
        this.w = w;
        this.h = h;
        line = new MazeLine(this, (OldLines) add(new OldLines(user, mDBApi, w, h, prefs, GAMEID)));
        add(line);
        add(new Goal(w / 2, h / 8, r*h/64));
        add(new Start(w/2, 7*h/8, r*h/64));

        timeout = Integer.parseInt(prefs.getString("num_paths_timing", "240")) *1000;


        //add(new Obstacle(w/5-h/10, h/2 - h/10, h/5, h/5));
        //add(new Obstacle(w/2-h/10, h/2 - h/10, h/5, h/5));
        //add(new Obstacle(4*w/5-h/10, h/2 - h/10, h/5, h/5));

    }

    private int fadestep = 0;
    @Override
    protected void updateLogic(){
        super.updateLogic();
        if ((line.start != -1) && (System.currentTimeMillis() - line.start > timeout)){ // Four minutes
            if (fadestep == 0) {
                line.setGameOver();
                fadestep += 1;
                add(new OpacityBox(0, 0, w, h));
                add(new TextBox(w / 2, h / 2, "Time Elapsed"));
                finish();
            }

        }
    }

}
