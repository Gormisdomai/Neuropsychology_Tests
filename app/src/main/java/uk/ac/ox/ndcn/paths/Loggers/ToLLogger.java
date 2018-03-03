package uk.ac.ox.ndcn.paths.Loggers;


import android.content.Context;

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
        this.optimalMoves = optimalMoves;
        super.logLine("-------------");
        super.logLine("startTime: " + System.currentTimeMillis());
    }

    public void startMove() {}
    public void endMove() {
        movecount ++;
    }
    public void done(Boolean won){
        super.logLine("solved?: " + won + ", moves: " + movecount + ", optimal: " + optimalMoves);
        super.logLine("endTime: " + System.currentTimeMillis());
    }
}
