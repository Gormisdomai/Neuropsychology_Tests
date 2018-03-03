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
    //  -
    //  -
    //  -

public class Logger {
    private String log = "";
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
        log += word;
    }

    public void upload(Context context) {
        try{
            UploadFile.save(logName + "_" + System.currentTimeMillis() + ".txt", log, mDBApi, context);
        }
        catch (DropboxException e){
            Log.d("err:", "dropb");
            Toast.makeText(context, "Failed to save to Dropbox", Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            Log.d("err:", "io");

            Toast.makeText(context, "Failed to write to Local Storage", Toast.LENGTH_LONG).show();
        }
    }
}
