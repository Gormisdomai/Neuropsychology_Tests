package uk.ac.ox.ndcn.paths.Games;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.preference.PreferenceManager;

import java.util.ArrayList;

import uk.ac.ox.ndcn.paths.GeneralEntities.OpacityBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.MazeEntities.*;
import uk.ac.ox.ndcn.paths.R;

/**
 * Created by appdev on 02/10/15.
 */
public class SwitchPathsView extends World {

    public MazeLine line;
    public TextBox textBox;
    private int timeout = 240000;
    public static final String GAMEID = "SWITCHPATHSVIEW";
    public int score = 0;

    public SwitchPathsView(Activity context, String _user) {

        super(context, _user);
        instructions.add(R.drawable.os4);
        instructions.add(R.drawable.os3);
        instructions.add(R.drawable.os2);
        instructions.add(R.drawable.os1);

    }

    @Override
    public void init () {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int r = Integer.parseInt(prefs.getString("blob_radius", "6"));
        int switchPoints = Integer.parseInt(prefs.getString("switch_points", "2"));
        if (switchPoints ==1) switchPoints = 2;
        line = (MazeLine) add(new MazeLine(this, (OldLines) add(new OldLinesSwitch(user, w, h, prefs, GAMEID))));
        line.greyline.setColor(Color.BLACK);
        add(new Goal(w / 16, h / 8, r * h / 64, new GoalBehaviour() {
            @Override
            public void run() {
                score += 1;
            }

            @Override

            public void draw(Canvas c) {

            }
        }, 1));
        add(new TextBox(w / 16, h / 8, "  1  "));


        ArrayList<Obstacle> subsequentObstacles = new ArrayList<Obstacle>();

        int obstacleWidth = 3*w/112;
        int obstacleHeight = h/8;

        int offsetX = 4*w/7;
        int offsetY = 0;

        int [] [] obstaclePositions = {
                {0,2},
                {0,1},
                {0,0},

                {2,1},
                {2,2},
                {2,3},

                {4,2},
                {4,1},
                {4,0},

                {6,1},
                {6,2},
                {6,3},

                {8,2},
                {8,1},
                {8,0},

                {10,1},
                {10,2},
                {10,3},

        };

        for (int [] i : obstaclePositions){
            int _x = i[0];
            int _y = i[1];
            subsequentObstacles.add(new Obstacle(
                    _x * obstacleWidth + offsetX, _y * obstacleHeight + offsetY,
                    obstacleWidth, obstacleHeight));
        }

        /*subsequentObstacles.add(new Obstacle(13*w/16, 0, h/30, h/5));
            subsequentObstacles.add(new Obstacle(12*w/16, h/7, h/30, h/5));
                subsequentObstacles.add(new Obstacle(11*w/16, 2*h/7, h/30, h/4));
        subsequentObstacles.add(new Obstacle(10*w/16, 0, h/30, h/5));
            subsequentObstacles.add(new Obstacle(9 * w / 16, h / 7, h / 30, h / 5));

            subsequentObstacles.add(new Obstacle(9 * w / 16, 2*h / 7, h / 30, h / 4));


                subsequentObstacles.add(new Obstacle(10 * w / 16, 2*h/7, h / 30, h / 4));
            subsequentObstacles.add(new Obstacle(11*w/16, h/7, h/30, h/4));

                subsequentObstacles.add(new Obstacle(13 * w / 16, 2 * h / 7, h / 30, h / 4));
            subsequentObstacles.add(new Obstacle(12 * w / 16, 0, h / 30, h / 4));
            */


        add(new Goal(15 * w / 16, h / 8, r * h / 64, new ObstacleSpawner(subsequentObstacles, this, switchPoints), switchPoints));
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
    protected void update(){
        textBox.text = "" + score;
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
}
