package uk.ac.ox.ndcn.paths.Loggers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.IOException;

import uk.ac.ox.ndcn.paths.GeneralEntities.UploadFile;

/**
 * Created by appdev on 02/03/2018.
 */

// Note Oldlines class for linedrawing games currently handle their own logging
    // This class is currently used for the following games (as of March 2018):
    //  - ToL
    //  -
    //  -

public class Logger {
    public boolean enabled = true;
    protected String log = "";
    private DropboxAPI mDBApi;
    private String logName = "";


    public Logger(String user, String game){
        logName = user + "_" + game;
    }

    public void logLine(String line){
        logWord(line);
        logWord("\n");
    }

    public void logWord(String word){
        if (! enabled) return;
        log += word;
    }

    public void upload(Context context) {
        try{
            UploadFile.save(logName + "_" + System.currentTimeMillis() + ".txt", log, context);
        }
        catch (DropboxException e){
            Log.e("saving", "Failed to save to dropbox");
            Toast.makeText(context, "Failed to save to Dropbox", Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            Log.e("saving", "Failed to write to local storage");

            Toast.makeText(context, "Failed to write to Local Storage", Toast.LENGTH_LONG).show();
        }
    }
}
