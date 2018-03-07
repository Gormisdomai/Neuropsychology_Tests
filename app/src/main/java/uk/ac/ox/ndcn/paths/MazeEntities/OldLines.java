package uk.ac.ox.ndcn.paths.MazeEntities;

/**
 * Created by appdev on 09/09/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Paint;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;


import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.UploadFile;


public class OldLines extends Entity {



    public Paint line = new Paint();

    private ArrayList<Path> paths = new ArrayList<Path>();

    public ArrayList<PathData> pathDatas = new ArrayList<PathData>();

    private DropboxAPI mDBApi;

    public Bitmap cache;
    private Canvas cacheCanvas = new Canvas();
    protected String game;


    public String user;

    public OldLines(String _user, DropboxAPI mDBApi, int w, int h, SharedPreferences prefs, String _game){

        game = _game;

        cache = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        cacheCanvas.setBitmap(cache);

        line.setAntiAlias(true);
        line.setDither(true);
        line.setColor(Color.DKGRAY);
        line.setStyle(Paint.Style.STROKE);
        line.setStrokeJoin(Paint.Join.ROUND);
        line.setStrokeCap(Paint.Cap.ROUND);
        line.setStrokeWidth(Integer.parseInt(prefs.getString("line_width", "6")));
        user = _user;
        this.mDBApi = mDBApi;

    }

    @Override
    public void draw(Canvas canvas) {
        /*for (Path p : paths) {
            canvas.drawPath(p, line);
        }*/
        canvas.drawBitmap(cache, 0, 0, line);
    }

    @Override
    public String toString() {
        String output = game + "\n";
        output += "Path Number, x, y, time\n";

        for (int pathnumber = 0; pathnumber < pathDatas.size(); pathnumber += 1){
            for (timePoint point : pathDatas.get(pathnumber).data){
                output += pathnumber + ", " + point.x + ", " + point.y + ", " + point.time + "\n";
            }
        }
        output += "--------\n";
        for (int pathnumber = 0; pathnumber < pathDatas.size(); pathnumber += 1){
            if(pathDatas.get(pathnumber).extraData() != "") {
                output += pathnumber + ", " + pathDatas.get(pathnumber).extraData() + "\n";
            }
        }
        return output;
    }



    public void save(Context context) {
        Log.d("Saving OldLines:", this.toString());
        try{
            UploadFile.save(user + "_" + game + "_" + System.currentTimeMillis() + ".txt", this.toString(), mDBApi, context);
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

    public void saveImage(Context context){
        try{
            UploadFile.saveImg(user + "_" + game + "_" + System.currentTimeMillis() + ".png", cache, mDBApi, context);
        }
        catch (DropboxException e){
            Log.d("err:", "dropb");
            Toast.makeText(context, "Failed to save image to Dropbox", Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            Log.d("err:", "io");

            Toast.makeText(context, "Failed to write image to Local Storage", Toast.LENGTH_LONG).show();
        }
    }

    public void add(Path p, PathData d){
        paths.add(p);
        pathDatas.add(d);
        cacheCanvas.drawPath(p, line);
    }

    public void reset(){
        paths.clear();
        pathDatas.clear();
        cache.eraseColor(Color.TRANSPARENT);
    }
}
