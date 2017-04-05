package uk.ac.ox.ndcn.paths.Games;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

import com.dropbox.client2.DropboxAPI;

import uk.ac.ox.ndcn.paths.GeneralEntities.OpacityBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.MazeEntities.*;

/**
 * Created by appdev on 02/10/15.
 */
public class SwitchPathsView extends World {

    public MazeLine line;
    private int timeout = 240000;
    public int w, h;
    public static final String GAMEID = "SWITCHPATHSVIEW";

    public SwitchPathsView(Activity context, String _user, DropboxAPI mDBApi) {
        super(context, _user, mDBApi);
    }
    @Override
    public void init (int w, int h) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int r = Integer.parseInt(prefs.getString("blob_radius", "6"));
        this.w = w;
        this.h = h;
        line = (MazeLine) add(new MazeLine(this, (OldLines) add(new OldLines(user, mDBApi, w, h, prefs, GAMEID))));
        add(new Goal(w/16, h/8, r*h/64));


        ArrayList<Obstacle> subsequentObstacles = new ArrayList<Obstacle>();

        subsequentObstacles.add(new Obstacle(13*w/16, 0, h/30, h/5));
        subsequentObstacles.add(new Obstacle(12*w/16, h/7, h/30, h/5));
        subsequentObstacles.add(new Obstacle(11*w/16, 0, h/30, h/5));
        subsequentObstacles.add(new Obstacle(10*w/16, h/7, h/30, h/5));
        subsequentObstacles.add(new Obstacle(9*w/16, 0, h/30, h/5));
        add(new Goal(15 * w / 16, h / 8, r*h/64, new ObstacleSpawner(subsequentObstacles, this)));
        add(new Start(w / 2, 7 * h / 8, r*h/64));

        //add(new TextBox(20,20,"TESTING ONLY"));

        add(new Obstacle(0, h / 3, 2 * w / 5, 3 * h / 3));
        add(new Obstacle(3*w/5, h / 3, 2*w/5, 2*h/3));


        timeout = Integer.parseInt(prefs.getString("switch_paths_timing", "240"))* 1000;


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
