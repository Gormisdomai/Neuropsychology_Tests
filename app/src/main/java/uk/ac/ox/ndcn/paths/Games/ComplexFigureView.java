package uk.ac.ox.ndcn.paths.Games;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.dropbox.client2.DropboxAPI;

import uk.ac.ox.ndcn.paths.ComplexFigureEntities.CanvasLine;
import uk.ac.ox.ndcn.paths.ButtonsAndKeyPads.DoneButton;
import uk.ac.ox.ndcn.paths.GeneralEntities.DoneHandler;
import uk.ac.ox.ndcn.paths.Util.Image;
import uk.ac.ox.ndcn.paths.GeneralEntities.OpacityBox;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;
import uk.ac.ox.ndcn.paths.MazeEntities.OldLines;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.R;

/**
 * Created by appdev on 25/04/2016.
 */
public class ComplexFigureView extends World implements DoneHandler {
    Bitmap figure;
    public CanvasLine line;
    public static final String GAMEID = "COMPLEXFIGUREVIEW";
    static final int COPY = 0;
    static final int REMEMBER = 1;
    static final int DONE = 2;
    int state = COPY;
    int w,h;
    DoneButton doneButton;
    Image image;
    OldLines history;
    int timeout;

    public ComplexFigureView(Activity context, String _user, DropboxAPI mDBApi) {
        super(context, _user, mDBApi);
        figure = BitmapFactory.decodeResource(getResources(), R.drawable.complex_figure);


    }
    @Override
    public void init (int _w, int _h) {
        w = _w;
        h = _h;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        image = (Image)add(new Image(0, 0, (int)(0.8*w), h, figure));
        setBackgroundColor(Color.WHITE);
        history = (OldLines) add(new OldLines(user, mDBApi, w, h, prefs, GAMEID));
        line = (CanvasLine) add(new CanvasLine(this, history));
        doneButton = (DoneButton)add(new DoneButton(0,  h-w/16, Math.max(h / 7, 100), w/16, this));
        timeout = Integer.parseInt(prefs.getString("complex_figure_timing", "240"))* 1000;
    }
    public void done(String s){
        nextState();
    }
    protected void updateLogic(){
        super.updateLogic();

        if ((line.start != -1) && (System.currentTimeMillis() - line.start > timeout)){ // Four minutes
            nextState();
        }
    }
    public void nextState(){
        switch (state){
            case COPY:
                line.done("");
                history.saveImage(getContext());
                remove(doneButton);
                remove(history);
                remove(line);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                //reset the line and the image
                history = (OldLines) add(new OldLines(user, mDBApi, w, h, prefs, GAMEID));
                line = (CanvasLine) add(new CanvasLine(this, history));
                doneButton = (DoneButton)add(new DoneButton(0, h-w/16, Math.max(h / 7, 100), w/16, this));
                image.done("");
                state += 1;
                break;
            case REMEMBER:
                line.done("");
                history.saveImage(getContext());
                state += 1;
                add(new OpacityBox(0, 0, w, h));
                add(new TextBox(w / 2, h / 2, "Done"));
                break;
            case DONE:
                finish();
                break;
        }
    }
}
