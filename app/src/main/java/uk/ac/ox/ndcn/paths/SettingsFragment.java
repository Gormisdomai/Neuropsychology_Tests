package uk.ac.ox.ndcn.paths;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.*;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.ac.ox.ndcn.paths.Games.GameLauncher;
import uk.ac.ox.ndcn.paths.Games.NumPathsView;


public class SettingsFragment extends PreferenceFragment{
    private String GameID;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameID = this.getArguments().getString("EXTRA_GAMEID");
        // Load the preferences from an XML resource, TODO maybe these should be passed in with gameID?
        int prefs =  GamesList.getPreferencesFromGameId(GameID);
        addPreferencesFromResource(prefs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout v = (LinearLayout) super.onCreateView(inflater, container, savedInstanceState);


        Button btn = new Button(getActivity().getApplicationContext());
        btn.setBackgroundColor(Color.RED);
        btn.setText("Start Game");

        v.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                launchGame();
            }
        });

        return v;
    }
    private void launchGame(){
        Intent intent = new Intent(getActivity(), GameLauncher.class);
        /*EditText usernameField = (EditText)findViewById(R.id.editText);
        String username = usernameField == null ? "" : usernameField.getText().toString();
        */
        String username = ""; //TODO input username or thread it through from the main homepage

        intent.putExtra("EXTRA_UID", username);
        intent.putExtra("EXTRA_GAMEID", GameID);
        startActivity(intent);
    }
}

