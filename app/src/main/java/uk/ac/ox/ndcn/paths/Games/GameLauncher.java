package uk.ac.ox.ndcn.paths.Games;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import uk.ac.ox.ndcn.paths.MainActivity;

/**
 * Created by appdev on 25/04/2016.
 */
public class GameLauncher extends Activity{
    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String UID = getIntent().getStringExtra("EXTRA_UID");
        String GameID = getIntent().getStringExtra("EXTRA_GAMEID");



        UID = UID == null ? "" : UID;
        switch (GameID){
            case ComplexFigureView.GAMEID:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                view = new ComplexFigureView(this, UID, MainActivity.mApi);
                break;
            case FluencyView.GAMEID:
                view = new FluencyView(this, UID, MainActivity.mApi);
                break;
            case NumPathsView.GAMEID:
                view = new NumPathsView(this, UID, MainActivity.mApi);
                break;
            case SwitchPathsView.GAMEID:
                view = new SwitchPathsView(this, UID, MainActivity.mApi);
                break;
            case TrailMakingView.GAMEID:
                view = new TrailMakingView(this, UID, MainActivity.mApi);
                break;
            case TolView.GAMEID:
                view = new TolView(this, UID, MainActivity.mApi);
                break;
            case VisualDigitSpanView.GAMEID:
                view = new VisualDigitSpanView(this, UID, MainActivity.mApi);
                break;
            case NumericalDigitSpanView.GAMEID:
                view = new NumericalDigitSpanView(this, UID, MainActivity.mApi);
                break;
        }

        view.setBackgroundColor(Color.BLACK);

        setContentView(view);

    }
}

