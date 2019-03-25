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

import uk.ac.ox.ndcn.paths.ButtonsAndKeyPads.DoneButton;

import uk.ac.ox.ndcn.paths.Loggers.ToLLogger;
import uk.ac.ox.ndcn.paths.R;
import uk.ac.ox.ndcn.paths.TowerOfLondonEntities.TargetPeg;
import uk.ac.ox.ndcn.paths.TowerOfLondonEntities.Peg;
import uk.ac.ox.ndcn.paths.GeneralEntities.DoneHandler;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;

import java.util.ArrayList;
import java.util.Random;

import uk.ac.ox.ndcn.paths.TowerOfLondonEntities.TolLevelGenerator;
import uk.ac.ox.ndcn.paths.Util.Image;

public class TolView extends World implements DoneHandler {

    private int state = 0;
    public static final String GAMEID = "TolView";
    public boolean drag_lock = false;

    public ToLLogger log;

    public ArrayList<Peg> pegs = new ArrayList<>();
    public ArrayList<TargetPeg> targetPegs = new ArrayList<>();


    Image target;


    public TolView(Activity context, String _user) {
        super(context, _user);
        instructions.add(R.drawable.tol3);
        instructions.add(R.drawable.tol2);
        instructions.add(R.drawable.tol1);

    }
    private boolean inited = false;
    private boolean mixHeights = false;
    private int maxDifficulty = 5;
    private int[] uniformHeights = {3,3,3};
    private boolean randomiseDifficulty = false;
    private int numTrials = 5;
    private int trialsSoFar = 1;
    private int minDifficulty = 1;
    private int currentDifficulty = 1;
    private int repeatDifficulty = 2;
    private int countTilNextDifficulty = 0;
    @Override
    public void init () {
        log = new ToLLogger(GAMEID, user, this.getContext());
        inited = true;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        /*float [][] pegData = {
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
        (new TolLevel(pegData, blockData, targetblockData)).build(this);*/

        mixHeights = prefs.getBoolean("tol_mixed_height", false);
        maxDifficulty = Integer.parseInt(prefs.getString("tol_max_difficulty", "5"));
        minDifficulty = Integer.parseInt(prefs.getString("tol_min_difficulty", "1"));
        repeatDifficulty = Integer.parseInt(prefs.getString("tol_repeat_difficulty", "1"));
        randomiseDifficulty = prefs.getBoolean("tol_randomise_difficulty", false);
        numTrials = Integer.parseInt(prefs.getString("tol_num_trials", "5"));

        currentDifficulty = minDifficulty;
        Random random = new Random();
        int[] heights = {random.nextInt(4) + 1, random.nextInt(4) + 2, random.nextInt(4) + 1};
        float [] colors = {Color.RED, Color.BLUE, Color.GREEN};
        TolLevelGenerator levelGenerator = new TolLevelGenerator(mixHeights ? heights : uniformHeights, colors);
        int optimal = levelGenerator.shuffleTarget(randomiseDifficulty ? random.nextInt(maxDifficulty) + minDifficulty : currentDifficulty);
        log.newLevel(optimal);
        levelGenerator.ConvertLevel().build(this);
        add(new DoneButton(0, 0, Math.max(w / 7, 100), h / 16, this));
        add(new DoneButton(Math.max(w / 7, 100) + 10, 0, Math.max(w / 7, 100), h/16, "End", this));
        countTilNextDifficulty ++;
        if (countTilNextDifficulty == repeatDifficulty){
            if (currentDifficulty <= maxDifficulty) {
                currentDifficulty ++;
            }
            countTilNextDifficulty = 0;
        }
    }

    @Override
    public void draw(Canvas c){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);
        super.draw(c);
        if (!inited) return;
        if (pegs.equals(targetPegs)){
            //c.drawCircle(0, 0, 100, paint);

        }



    }

    public void done(String s){
        if (s == "End" || trialsSoFar >= numTrials){
            log.save();
            finish();
            //TODO add an end button that triggers this or trigger it when out of tasks
        }
        else {
            log.done(this.pegs.equals(this.targetPegs));
            trialsSoFar += 1;
            nextState();
        }
    }

    @Override
    public void saveAndQuit(){
        log.save();
        super.saveAndQuit();
    }

    public void update(){
        super.update();
    }

    private Random random = new Random();
    public void nextState(){
        removeAll(entities);
        int[] heights = {random.nextInt(4) + 1, random.nextInt(4) + 2, random.nextInt(4) + 1};
        float[] colors = {Color.RED, Color.BLUE, Color.GREEN};
        TolLevelGenerator levelGenerator = new TolLevelGenerator(mixHeights ? heights : uniformHeights, colors);
        int optimal = levelGenerator.shuffleTarget(randomiseDifficulty ? random.nextInt(maxDifficulty) + minDifficulty : currentDifficulty);
        log.newLevel(optimal);
        levelGenerator.ConvertLevel().build(this);
        add(new DoneButton(0, 0, Math.max(w / 7, 100), h / 16, this));
        add(new DoneButton(Math.max(w / 7, 100) + 10, 0, Math.max(w / 7, 100), h/16, "End", this));
        countTilNextDifficulty ++;
        if (countTilNextDifficulty == repeatDifficulty){
            if (currentDifficulty <= maxDifficulty) {
                currentDifficulty ++;
            }
            countTilNextDifficulty = 0;
        }

    }
   /* public void nextState(){
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
            case 2:
                removeAll(entities);
                float [][] pegData1 = {
                        {1f/5,3},
                        {2f/5,1},
                        {3f/5,4},
                        {4f/5,4},
                };
                float [][] blockData1 = {
                        {1f/5, 3, Color.GREEN},
                        {2f/5, 1, Color.BLUE},
                        {3f/5, 1, Color.RED},
                        {4f/5, 1, Color.YELLOW},

                };
                float [][] targetblockData1 = {
                        {1f/5, 1, Color.YELLOW},
                        {2f/5, 1, Color.GREEN},
                        {3f/5, 2, Color.BLUE},
                        {4f/5, 3, Color.RED}

                };
                (new TolLevel(pegData1, blockData1, targetblockData1)).build(this);
                add(new DoneButton(0, 0, Math.max(w / 7, 100), h / 16, this));
                break;
            case 3:
                removeAll(entities);
                float [][] pegData2 = {
                        {1f/4,3},
                        {1f/2,1},
                        {3f/4,4},
                };
                float [][] blockData2 = {
                        {3f/4, 3, Color.GREEN},
                        {3f/4, 2, Color.RED},
                        {3f/4, 1, Color.BLUE},

                };
                float [][] targetblockData2 = {
                        {3f/4, 1, Color.GREEN},
                        {3f/4, 2, Color.BLUE},
                        {3f/4, 3, Color.RED}

                };
                (new TolLevel(pegData2, blockData2, targetblockData2)).build(this);
                add(new DoneButton(0, 0, Math.max(w / 7, 100), h / 16, this));
                break;
            case 4:
                removeAll(entities);
                float [][] pegData3 = {
                        {1f/4,3},
                        {3f/4,4},
                };
                float [][] blockData3 = {
                        {3f/4, 3, Color.GREEN},
                        {3f/4, 2, Color.BLUE},
                        {3f/4, 1, Color.RED},

                };
                float [][] targetblockData3 = {
                        {3f/4, 1, Color.GREEN},
                        {3f/4, 2, Color.BLUE},
                        {3f/4, 3, Color.RED}

                };
                (new TolLevel(pegData3, blockData3, targetblockData3)).build(this);
                add(new DoneButton(0, 0, Math.max(w / 7, 100), h / 16, this));
                break;
            default:
                removeAll(entities);
                int [] heights = {1,3,4};
                float [] colors = {Color.RED, Color.BLUE, Color.GREEN};
                TolLevelGenerator levelGenerator = new TolLevelGenerator(heights, colors);
                levelGenerator.shuffleTarget(5);
                levelGenerator.ConvertLevel().build(this);
                add(new DoneButton(0, 0, Math.max(w / 7, 100), h / 16, this));
                break;
        }

    }*/


}
