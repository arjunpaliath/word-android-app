package koundinya.com.wordquiz.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import koundinya.com.wordquiz.R;
import koundinya.com.wordquiz.model.User;

/**
 * Created by girishk on 12/05/15.
 */
public class ScoreListAdapter extends ArrayAdapter<User> {


    private  Context context;
    private  ArrayList<User> values;
    private  int viewRes;
    private  User user;

    public ScoreListAdapter(Context context, int score_list_item, ArrayList<User> list_scores) {

        super(context, score_list_item, list_scores);
        this.values = new ArrayList<User>();
        this.context = context;
        this.values  = list_scores;
        this.viewRes =score_list_item;
    }

    @Override
    public int getCount() {
        return values.size();
    }
    @Override
    public User getItem(int pos) {
        return values.get(pos);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(this.viewRes, parent, false);

        TextView other_name  = (TextView) rowView.findViewById(R.id.other_name);
        TextView other_score = (TextView) rowView.findViewById(R.id.other_score);

        user  = values.get(position);

        other_name.setText(user.name);
        other_score.setText(Integer.toString(user.score));

        return rowView;
    }

}