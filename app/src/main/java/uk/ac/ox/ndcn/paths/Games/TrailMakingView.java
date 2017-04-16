package uk.ac.ox.ndcn.paths.Games;

/**
 * Created by appdev on 27/03/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;

import uk.ac.ox.ndcn.paths.GeneralEntities.OpacityBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.MazeEntities.OldLines;
import uk.ac.ox.ndcn.paths.TrailEntities.Trail;
import uk.ac.ox.ndcn.paths.TrailEntities.TrailLine;

public class TrailMakingView extends World {
    public static final String GAMEID = "TRAILMAKINGVIEW";
    int w, h;
    int timeout;
    private uk.ac.ox.ndcn.paths.TrailEntities.TrailLine line;
    public TrailMakingView(Activity context, String _user, DropboxAPI mDBApi) {
        super(context, _user, mDBApi);

    }
    @Override
    public void init (int _w, int _h) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        w = _w;
        h = _h;
        Trail t = new Trail(10, !prefs.getBoolean("trail_letters", false), prefs.getBoolean("trail_feedback",false), 0, 0, w, h, this);
        line = new TrailLine(this, (OldLines)add(new OldLines(user, mDBApi, w ,h, prefs, GAMEID)), t);
        add(line);
        add(t);
        timeout = Integer.parseInt(prefs.getString("trail_timing", "240"))* 1000;



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
