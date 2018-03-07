package uk.ac.ox.ndcn.paths.MazeEntities;

import android.content.SharedPreferences;
import android.graphics.Path;

import com.dropbox.client2.DropboxAPI;

import java.util.ArrayList;

/**
 * Created by appdev on 06/03/2018.
 */
public class OldLinesSwitch extends OldLines {
    public int score = 0;
    public int lastScore = -1;
    public ArrayList<Long> switches = new ArrayList<Long>();
    public OldLinesSwitch(String _user, DropboxAPI mDBApi, int w, int h, SharedPreferences prefs, String _game){
        super(_user, mDBApi, w, h, prefs, _game);
    }


    @Override
    public void add(Path p, PathData d){
        super.add(p, d);
        score += d.goalData;
        if (lastScore == -1) {
            lastScore = score;
            return;
        }
        else if (lastScore != score){
            switches.add(d.start);
            lastScore = score;
        }
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

        output += "Total Score: " + score + "\n";
        if (switches.size() == 0) {
            output += "Never Switched.";
        }
        else {
            output += "Time of First Switch: " + switches.get(0) + "\n";
            output += "Subsequent Switches:" + "\n";
            for (int i = 1; i < switches.size(); i++) {
                output += switches.get(i) + "\n";
            }
        }
        return output;
    }
}
