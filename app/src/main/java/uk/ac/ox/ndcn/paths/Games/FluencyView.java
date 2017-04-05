package uk.ac.ox.ndcn.paths.Games;

/**
 * Created by appdev on 27/03/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dropbox.client2.DropboxAPI;

import uk.ac.ox.ndcn.paths.DraggingEntities.TowerBlock;
import uk.ac.ox.ndcn.paths.GeneralEntities.OpacityBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.Timer;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.FluencyEntities.Trail;
import uk.ac.ox.ndcn.paths.FluencyEntities.TrailLine;
import uk.ac.ox.ndcn.paths.FluencyEntities.OldLines;

public class FluencyView extends World {

    public TrailLine line;
    public int w;
    public int h;
    private int timeout = 240000;
    public static final String GAMEID = "FLUENCYVIEW";
    private Timer timer;

    public FluencyView(Activity context, String _user, DropboxAPI mDBApi) {
        super(context, _user, mDBApi);

    }
    @Override
    public void init (int _w, int _h) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        w = _w;
        h = _h;
        Trail t = new Trail(5, true, 0, 0, w, h, this);
        line = (TrailLine)add(new TrailLine(this, new OldLines(user, mDBApi, w, h, prefs, GAMEID), t));
        add(t);
        timeout = Integer.parseInt(prefs.getString("fluency_timing", "240")) * 1000;
        timer = ((Timer)add(new Timer(w/2,20)));


    }


    private int fadestep = 0;
    @Override
    protected void updateLogic(){
        super.updateLogic();
        if(line.start != -1 && !timer.running && fadestep == 0){
            timer.start(timeout);
        }
        if ((line.start != -1) && (System.currentTimeMillis() - line.start > timeout)){ // Four minutes
            timer.stop();
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
