package info.androidhive.swiperefresh.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import info.androidhive.swiperefresh.R;

/**
 * Created by Ravi on 13/05/15.
 */
public class SwipeListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Movie> movieList;

    private List<Grade> gradeList;


    private String[] bgColors;

    /*public SwipeListAdapter(Activity activity, List<Movie> movieList) {
        this.activity = activity;
        this.movieList = movieList;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);
    }*/

    public SwipeListAdapter(Activity activity, List<Grade> gradeList) {
        this.activity = activity;
        this.gradeList = gradeList;
        bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);
    }

    /*@Override
    public int getCount() {
        return movieList.size();
    }*/
    @Override
    public int getCount() {
        return gradeList.size();
    }

    /*@Override
    public Object getItem(int location) {
        return movieList.get(location);
    }*/
    @Override
    public Object getItem(int location) {
        return gradeList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView serial = (TextView) convertView.findViewById(R.id.serial);
        TextView title = (TextView) convertView.findViewById(R.id.title);

        serial.setText(String.valueOf(gradeList.get(position).teste1));
        title.setText(String.valueOf(gradeList.get(position).teste2));

        String color = bgColors[position % bgColors.length];
        serial.setBackgroundColor(Color.parseColor(color));

        return convertView;
    }

}