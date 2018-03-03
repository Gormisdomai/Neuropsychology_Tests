package uk.ac.ox.ndcn.paths.Loggers;


import android.content.Context;
import android.util.Log;

/**
 * Created by appdev on 02/03/2018.
 */
public class ToLLogger extends Logger {

    private int movecount = 0;
    private int optimalMoves = 0;
    private Context context;

    public ToLLogger(String user, String game, Context context){
        super(user, game);
        this.context = context;
    }

    public ToLLogger(String user, String game, int optimalMoves, Context context){
        super(user, game);
        this.context = context;
        newLevel(optimalMoves);
    }

    public void newLevel(int optimalMoves){
        if (!enabled) return;
        this.movecount = 0;
        this.optimalMoves = optimalMoves;
        super.logLine("-------------");
        super.logLine("startTime: " + System.currentTimeMillis());
    }

    public void startMove(String name) {
        if (!enabled) return;
        super.logWord("Move from Peg " + name);
    }
    public void cancelMove() {
        if (!enabled) return;
        super.logLine(" move cancelled.");
    }
    public void endMove(String name) {
        if (!enabled) return;
        super.logLine(" move to Peg " + name);
        movecount ++;
    }
    public void done(Boolean won){
        if (!enabled) return;
        super.logLine("solved?: " + won + ", moves: " + movecount + ", optimal: " + optimalMoves);
        super.logLine("endTime: " + System.currentTimeMillis());
        Log.d("TolLogger", log);
    }
    public void save(){
        upload(context);
    }
}
