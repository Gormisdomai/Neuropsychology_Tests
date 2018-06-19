package uk.ac.ox.ndcn.paths;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ListView;


import com.dropbox.core.android.Auth;

import uk.ac.ox.ndcn.paths.Menutest.CustomList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Dropbox";

    ///////////////////////////////////////////////////////////////////////////
    //                      Your app-specific settings.                      //
    ///////////////////////////////////////////////////////////////////////////

    // Replace this with your app key and secret assigned by Dropbox.
    // Note that this is a really insecure way to do this, and you shouldn't
    // ship code which contains your key & secret in such an obvious way.
    // Obfuscation is good.
    final static public String APP_KEY = "xnht8p5v29tsxfn";
    final static public String APP_SECRET = "eg5c03k3f1mj51h";

    ///////////////////////////////////////////////////////////////////////////
    //                      End app-specific settings.                       //
    ///////////////////////////////////////////////////////////////////////////

    // You don't need to change these, leave them alone.
    public static final String ACCOUNT_PREFS_NAME = "prefs";
    public static final String ACCESS_KEY_NAME = "ACCESS_KEY";
    public static final String ACCESS_SECRET_NAME = "ACCESS_SECRET";


    public static String accessToken;

    private Button mSubmit;


    private boolean mLoggedIn;

    View view;

    ListView list;


    /*public void launchPaths(View oldview){ launch(NumPathsView.GAMEID); }
    public void launchTol(View oldview) {launch(TolView.GAMEID);}
    public void launchMaze(View oldview){  launch(SwitchPathsView.GAMEID); }
    public void launchTrails(View oldview){ launch(TrailMakingView.GAMEID); }
    public void launchFluency(View oldview){ launch(FluencyView.GAMEID);  }
    public void launchFigure(View oldview){     launch(ComplexFigureView.GAMEID); }
8*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // We create a new AuthSession so that we can use the Dropbox API.
        //AndroidAuthSession session = buildSession();
        //mApi = new DropboxAPI<AndroidAuthSession>(session);

        // Basic Android widgets
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        Toolbar myToolbar = (Toolbar) findViewById(R.id._toolbar);
        setSupportActionBar(myToolbar);

        mSubmit = (Button)findViewById(R.id.button3);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // This logs you out if you're logged in, or vice versa
                if (mLoggedIn) {

                } else {
                    Auth.startOAuth2Authentication(getApplicationContext(), getString(R.string.APP_KEY));
                }
            }
        });

        CustomList adapter = new
                CustomList(MainActivity.this, GamesList.gameNames,  GamesList.gameIDs);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                launch(GamesList.gameIDs[+position]);
            }
        });


    }

    /*protected void onResume() {
        super.onResume();
        AndroidAuthSession session = mApi.getSession();

        // The next part must be inserted in the onResume() method of the
        // activity from which session.startAuthentication() was called, so
        // that Dropbox authentication completes properly.
        if (session.authenticationSuccessful()) {
            try {
                // Mandatory call to complete the auth
                session.finishAuthentication();

                // Store it locally in our app for later use
                storeAuth(session);
                setLoggedIn(true);
            } catch (IllegalStateException e) {
                showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
                Log.i(TAG, "Error authenticating", e);
            }
        }
    }*/
    @Override
    protected void onResume() {
        super.onResume();
        getAccessToken();
    }
    public void getAccessToken() {
        accessToken = Auth.getOAuth2Token(); //generate Access Token
        if (accessToken != null) {
            //Store accessToken in SharedPreferences
            SharedPreferences prefs = getSharedPreferences("com.example.valdio.dropboxintegration", Context.MODE_PRIVATE);
            prefs.edit().putString("access-token", accessToken).apply();
            setLoggedIn(true);
        } else {
            showToast("Couldn't authenticate with Dropbox:");
            Log.i(TAG, "Error authenticating");
        }
    }


    /**
     * Convenience function to change UI state based on being logged in
     */
    private void setLoggedIn(boolean loggedIn) {
        mLoggedIn = loggedIn;
        if (loggedIn) {
            mSubmit.setText("Logged In");
            mSubmit.setEnabled(false);
        } else {
            mSubmit.setText("Link with Dropbox");
        }
    }


    private void showToast(String msg) {
        Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        error.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    public void launch(String GameID){
        Intent intent = new Intent(this, SettingsActivity.class);
        //Intent intent = new Intent(this, GameLauncher.class);
        /*EditText usernameField = (EditText)findViewById(R.id.editText);
        String username = usernameField == null ? "" : usernameField.getText().toString();

        intent.putExtra("EXTRA_UID", username);*/
        intent.putExtra("EXTRA_GAMEID", GameID);
        startActivity(intent);
    }
}
