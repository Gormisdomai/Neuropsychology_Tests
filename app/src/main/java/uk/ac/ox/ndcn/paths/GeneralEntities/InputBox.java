package uk.ac.ox.ndcn.paths.GeneralEntities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by appdev on 08/01/2017.
 */
public class InputBox extends Entity {

    private float x;
    private float y;
    Paint paint = new Paint();
    LinearLayout lL;
    EditText edtTextView;
    Context context;
    public InputBox(float x, float y, Context context)
    {

        this.x = x;
        this.y = y;
        this.context = context;
    }

    @Override
    public void draw(Canvas canvas){
        lL = new LinearLayout(context);
        edtTextView = new EditText(context);
        edtTextView.setVisibility(View.VISIBLE);
        lL.addView(edtTextView);
        lL.measure(canvas.getWidth(), canvas.getHeight());
        canvas.translate(x, y);
        lL.layout(0, 0, canvas.getWidth(), canvas.getHeight());
        lL.draw(canvas);
        canvas.translate(-x, -y);
    }

}
