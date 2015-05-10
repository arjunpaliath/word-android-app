package koundinya.com.wordquiz.view.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import koundinya.com.wordquiz.model.Question;

/**
 * Created by girishk on 10/05/15.
 */
public class ChoiceListAdapter extends ArrayAdapter<Question>{


    public ChoiceListAdapter(Context context, int resource) {
        super(context, resource);
    }
}
