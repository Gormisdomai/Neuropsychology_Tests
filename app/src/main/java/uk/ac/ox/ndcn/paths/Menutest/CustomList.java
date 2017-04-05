package uk.ac.ox.ndcn.paths.Menutest;

/**
 * Created by appdev on 24/11/2016.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import uk.ac.ox.ndcn.paths.R;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] gameNames;
    private final String[] gameIDs;
    public CustomList(Activity context,
                      String[] gameNames, String[] gameIDs) {
        super(context, R.layout.list_single, gameNames);
        this.context = context;
        this.gameNames = gameNames;
        this.gameIDs = gameIDs;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(gameNames[position]);

        //imageView.setImageResource(imageId[position]);
        return rowView;
    }
}

