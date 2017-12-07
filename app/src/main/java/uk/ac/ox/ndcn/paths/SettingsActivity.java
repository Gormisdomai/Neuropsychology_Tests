package uk.ac.ox.ndcn.paths;

import android.app.Activity;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.ox.ndcn.paths.Games.GameLauncher;


public class SettingsActivity extends Activity {
    //TODO rewrite this so that it launches an app of your choice after choices have been made. call that app from GameLauncher.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //map the gameIDs to relevant settings to be displayed.

        super.onCreate(savedInstanceState);

        //setcontent view and then an app luanch button.
        // Display the fragment as the main content.
        SettingsFragment frag = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString("EXTRA_GAMEID", getIntent().getStringExtra("EXTRA_GAMEID"));
        frag.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, frag)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
