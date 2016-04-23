package com.ericmschmidt.latinreader.layouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ericmschmidt.latinreader.R;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;


/**
 * Creates a ListView item layout that displays a work's author and title.
 */
public class LibraryListViewAdapter extends ArrayAdapter<WorkInfo> {

    protected final Context context;
    protected final WorkInfo[] values;

    public LibraryListViewAdapter(Context context, WorkInfo[] values) {
        super(context, -1, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.library_listviewitem, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.listviewitem_title);
        title.setText(values[position].getTitle());
        TextView author = (TextView) rowView.findViewById(R.id.listviewitem_author);
        author.setText(values[position].getAuthor());

        return rowView;
    }
}
