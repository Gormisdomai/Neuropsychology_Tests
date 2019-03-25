package uk.ac.ox.ndcn.paths.Games;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import uk.ac.ox.ndcn.paths.GeneralEntities.World;

/**
 * Created by appdev on 25/04/2016.
 */
public class GameLauncher extends Activity{
    World view;

    @Override
    public void onBackPressed(){
        view.saveAndQuit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String UID = getIntent().getStringExtra("EXTRA_UID");
        String GameID = getIntent().getStringExtra("EXTRA_GAMEID");

        //TODO instead of a dropbox API we now pass a dropbox session

        UID = UID == null ? "" : UID;
        switch (GameID){
            case ComplexFigureView.GAMEID:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                view = new ComplexFigureView(this, UID);
                break;
            case FluencyView.GAMEID:
                view = new FluencyView(this, UID);
                break;
            case NumPathsView.GAMEID:
                view = new NumPathsView(this, UID);
                break;
            case SwitchPathsView.GAMEID:
                view = new SwitchPathsView(this, UID);
                break;
            case TrailMakingView.GAMEID:
                view = new TrailMakingView(this, UID);
                break;
            case TolView.GAMEID:
                view = new TolView(this, UID);
                break;
            case VisualDigitSpanView.GAMEID:
                view = new VisualDigitSpanView(this, UID);
                break;
            case NumericalDigitSpanView.GAMEID:
                view = new NumericalDigitSpanView(this, UID);
                break;
        }

        view.setBackgroundColor(Color.BLACK);

        setContentView(view);

    }
}

