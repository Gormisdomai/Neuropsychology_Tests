package uk.ac.ox.ndcn.paths.Games;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.preference.PreferenceManager;

import java.util.ArrayList;

import com.dropbox.client2.DropboxAPI;

import org.w3c.dom.Text;

import uk.ac.ox.ndcn.paths.GeneralEntities.OpacityBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.MazeEntities.*;

/**
 * Created by appdev on 02/10/15.
 */
public class SwitchPathsView extends World {

    public MazeLine line;
    public TextBox textBox;
    private int timeout = 240000;
    public int w, h;
    public static final String GAMEID = "SWITCHPATHSVIEW";
    public int score = 0;

    public SwitchPathsView(Activity context, String _user, DropboxAPI mDBApi) {
        super(context, _user, mDBApi);
    }

    @Override
    public void init (int w, int h) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int r = Integer.parseInt(prefs.getString("blob_radius", "6"));
        int switchPoints = Integer.parseInt(prefs.getString("switch_points", "2"));
        this.w = w;
        this.h = h;
        line = (MazeLine) add(new MazeLine(this, (OldLines) add(new OldLines(user, mDBApi, w, h, prefs, GAMEID))));
        line.greyline.setColor(Color.BLACK);
        add(new Goal(w / 16, h / 8, r * h / 64, new GoalBehaviour() {
            @Override
            public void run() {
                score += 1;
            }

            @Override
            public void draw(Canvas c) {

            }
        }));
        add(new TextBox(w / 16, h / 8, "  1  "));


        ArrayList<Obstacle> subsequentObstacles = new ArrayList<Obstacle>();

        subsequentObstacles.add(new Obstacle(13*w/16, 0, h/30, h/5));
            subsequentObstacles.add(new Obstacle(12*w/16, h/7, h/30, h/5));
                subsequentObstacles.add(new Obstacle(11*w/16, 2*h/7, h/30, h/4));
        subsequentObstacles.add(new Obstacle(10*w/16, 0, h/30, h/5));
            subsequentObstacles.add(new Obstacle(9 * w / 16, h / 7, h / 30, h / 5));

            subsequentObstacles.add(new Obstacle(9 * w / 16, 2*h / 7, h / 30, h / 4));


                subsequentObstacles.add(new Obstacle(10 * w / 16, 2*h/7, h / 30, h / 4));
            subsequentObstacles.add(new Obstacle(11*w/16, h/7, h/30, h/4));

                subsequentObstacles.add(new Obstacle(13 * w / 16, 2 * h / 7, h / 30, h / 4));
            subsequentObstacles.add(new Obstacle(12 * w / 16, 0, h / 30, h / 4));


        add(new Goal(15 * w / 16, h / 8, r * h / 64, new ObstacleSpawner(subsequentObstacles, this, switchPoints)));
        add (new TextBox(15 * w / 16, h / 8, "  " + switchPoints + "  "));
        add(new Start(w / 2, 7 * h / 8, r*h/64));
        //add(new TextBox(20,20,"TESTING ONLY"));

        add(new Obstacle(0, h / 2, 3 * w / 7, h / 2));
        add(new Obstacle(4*w/7, h / 2, 3*w/7, h/2));

        textBox = (TextBox)add(new TextBox(w/2 - 10, 20, "0"));


        timeout = Integer.parseInt(prefs.getString("switch_paths_timing", "240"))* 1000;


    }

    private int fadestep = 0;
    @Override
    protected void updateLogic(){
        textBox.text = "" + score;
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
