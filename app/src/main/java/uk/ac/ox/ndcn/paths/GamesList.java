package uk.ac.ox.ndcn.paths;

import uk.ac.ox.ndcn.paths.Games.ComplexFigureView;
import uk.ac.ox.ndcn.paths.Games.FluencyView;
import uk.ac.ox.ndcn.paths.Games.NumPathsView;
import uk.ac.ox.ndcn.paths.Games.NumericalDigitSpanView;
import uk.ac.ox.ndcn.paths.Games.SwitchPathsView;
import uk.ac.ox.ndcn.paths.Games.TolView;
import uk.ac.ox.ndcn.paths.Games.TrailMakingView;
import uk.ac.ox.ndcn.paths.Games.VisualDigitSpanView;

/**
 * Created by appdev on 06/11/2017.
 */
public final class GamesList {

    public static String[] gameIDs = {
            NumPathsView.GAMEID,
            SwitchPathsView.GAMEID,
            TrailMakingView.GAMEID,
            FluencyView.GAMEID,
            ComplexFigureView.GAMEID,
            TolView.GAMEID/*,
            VisualDigitSpanView.GAMEID,
            NumericalDigitSpanView.GAMEID*/
    } ;

    public static String[] gameNames = {
            "Option Generation",
            "Option Switching",
            "Trail Making",
            "Design Fluency",
            "Complex Figure",
            "Tower of London" /*,
            "Shape Span",
            "Digit Span"*/
    } ;

    //maps gameIds to preference files.
    public static int[] gamePreferences = {
            R.xml.num_paths_preferences,// NumPathsView.GAMEID,
            R.xml.switch_paths_preferences,//SwitchPathsView.GAMEID,
            R.xml.trail_making_preferences,//TrailMakingView.GAMEID,
            R.xml.fluencey_preferences,//FluencyView.GAMEID,
            R.xml.complex_figure_preferences,//ComplexFigureView.GAMEID,
            R.xml.tol_preferences /*,//TolView.GAMEID,
            R.xml.visual_digit_span_preferences,//VisualDigitSpanView.GAMEID,
            R.xml.numerical_digit_span_preferences //NumericalDigitSpanView.GAMEID*/
    } ;

    public static int getPreferencesFromGameId(String GameID){
        return gamePreferences[java.util.Arrays.asList(GamesList.gameIDs).indexOf(GameID)];
    }


}
