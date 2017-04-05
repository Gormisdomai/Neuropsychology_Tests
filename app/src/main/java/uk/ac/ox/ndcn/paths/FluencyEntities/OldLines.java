package uk.ac.ox.ndcn.paths.FluencyEntities;

/**
 * Created by appdev on 09/09/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.UploadFile;
import uk.ac.ox.ndcn.paths.MazeEntities.PathData;
import uk.ac.ox.ndcn.paths.MazeEntities.timePoint;


public class OldLines extends Entity {



    public Paint line = new Paint();

    private ArrayList<Path> paths = new ArrayList<Path>();

    public ArrayList<PathData> pathDatas = new ArrayList<PathData>();

    private DropboxAPI mDBApi;

    public Bitmap cache;
    private Canvas cacheCanvas = new Canvas();
    private String game;


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
        output += "Path Number, Path Taken, Number of Other Paths Which Are the Same\n";
        HashMap<String,Integer> pathDatasNoReps = new HashMap<>();
        for (int pathnumber = 0; pathnumber < pathDatas.size(); pathnumber += 1){
            String k = pathDatas.get(pathnumber).extraData();

            if(pathDatasNoReps.containsKey(k)){
                pathDatasNoReps.put(k, pathDatasNoReps.get(k) + 1);
            }
            else{
                pathDatasNoReps.put(k, 0);

            }
        }
        for (int pathnumber = 0; pathnumber < pathDatasNoReps.size(); pathnumber += 1){
            String k = pathDatas.get(pathnumber).extraData();
            if(k != "") {
                output += pathnumber + ", " + k + ", " + pathDatasNoReps.get(k) + "\n";
            }

        }
        Log.d("TESTETSTESTE", output);
        return output;
    }



    public void save(Context context) {
        try{
            UploadFile.save(user + "_" + game + "_" + System.currentTimeMillis() + ".txt", this.toString(), mDBApi, context);
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
}
