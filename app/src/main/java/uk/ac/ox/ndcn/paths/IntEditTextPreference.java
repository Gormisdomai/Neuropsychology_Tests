package uk.ac.ox.ndcn.paths;

import android.preference.EditTextPreference;
import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by appdev on 24/04/2016.
 */
public class IntEditTextPreference extends EditTextPreference {
    public IntEditTextPreference(Context context) {
        super(context);
    }



    @Override
    public CharSequence getSummary (){
        return String.format(super.getSummary().toString(), getText() == null ? "" : getText());
    }
}
