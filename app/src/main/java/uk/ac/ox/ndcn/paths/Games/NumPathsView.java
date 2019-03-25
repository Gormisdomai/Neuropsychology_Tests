package uk.ac.ox.ndcn.paths.Games;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.MotionEvent;

import java.util.ArrayList;

import uk.ac.ox.ndcn.paths.GeneralEntities.DoneHandler;
import uk.ac.ox.ndcn.paths.GeneralEntities.OpacityBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.Timer;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.MazeEntities.*;
import uk.ac.ox.ndcn.paths.R;
import uk.ac.ox.ndcn.paths.Util.InstructionSlideShow;


/**
 * This class implements a canvas "world" onto which we can draw paths
 */
public class NumPathsView extends World {

    public static final String GAMEID = "NUMPATHSVIEW";
    public MazeLine line;
    private int timeout = 240000;

    public NumPathsView(Activity context, String _user) {
        super(context, _user);
        instructions.add(R.drawable.gen_4);
        //l.add(R.drawable.gen_3);
        instructions.add(R.drawable.gen_2);
        instructions.add(R.drawable.gen_1);

    }

    @Override
    public void init () {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int r = Integer.parseInt(prefs.getString("blob_radius", "6"));
        line = new MazeLine(this, (OldLines) add(new OldLines(user, w, h, prefs, GAMEID)));
        add(line);
        add(new Goal(w / 2, h / 8, r*h/64));
        add(new Start(w/2, 7*h/8, r*h/64));
        timer = new Timer(100, 100);
        add(timer);

        timeout = Integer.parseInt(prefs.getString("num_paths_timing", "240")) *1000;


        //add(new Obstacle(w/5-h/10, h/2 - h/10, h/5, h/5));
        //add(new Obstacle(w/2-h/10, h/2 - h/10, h/5, h/5));
        //add(new Obstacle(4*w/5-h/10, h/2 - h/10, h/5, h/5));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(!tutorial_mode && !timer.running) timer.start(timeout);
        return super.onTouchEvent(event);
    }


    Timer timer;

    private int fadestep = 0;
    @Override
    protected void update(){
        super.update();

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
    @Override
    public void saveAndQuit(){
        line.setGameOver();
        super.saveAndQuit();
    }

}
