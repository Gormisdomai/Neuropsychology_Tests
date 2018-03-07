package uk.ac.ox.ndcn.paths.TrailEntities;

import android.content.SharedPreferences;
import android.graphics.Path;

import com.dropbox.client2.DropboxAPI;

import java.util.ArrayList;

import uk.ac.ox.ndcn.paths.MazeEntities.OldLines;
import uk.ac.ox.ndcn.paths.MazeEntities.PathData;
import uk.ac.ox.ndcn.paths.MazeEntities.timePoint;

/**
 * Created by appdev on 06/03/2018.
 */
public class OldLinesTrail extends OldLines {
    public ArrayList<Long> switches = new ArrayList<Long>();
    public OldLinesTrail(String _user, DropboxAPI mDBApi, int w, int h, SharedPreferences prefs, String _game){
        super(_user, mDBApi, w, h, prefs, _game);
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
        for (int pathnumber = 0; pathnumber < pathDatas.size(); pathnumber += 1){
            if(pathDatas.get(pathnumber).extraData() != "") {
                output += pathnumber + ", " + pathDatas.get(pathnumber).extraData() + "\n";
            }
        }

        return output;
    }
}
