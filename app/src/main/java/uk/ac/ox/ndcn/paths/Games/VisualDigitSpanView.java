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

import com.dropbox.client2.DropboxAPI;

import uk.ac.ox.ndcn.paths.ButtonsAndKeyPads.Noisepad;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;

public class VisualDigitSpanView extends World {


    public static final String GAMEID = "VisualDigitSpanView";
    private Noisepad k;

    public VisualDigitSpanView(Activity context, String _user) {
        super(context, _user);

    }
    @Override
    public void init () {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        k = (Noisepad)add(new Noisepad(Integer.parseInt(prefs.getString("shape_span_count", "9")), false, 0, 0, w, h, this, 1500));
    }

    @Override
    public void draw(Canvas c){
        super.draw(c);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(1);
        if(k.input.equals(k.target)){
            c.drawCircle(0,0,100, paint);
            //finish();
        };
    }


}
