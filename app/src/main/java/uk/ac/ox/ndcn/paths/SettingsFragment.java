package uk.ac.ox.ndcn.paths;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.preference.*;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SettingsFragment extends PreferenceFragment{


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }


}
