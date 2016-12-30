package com.ericmschmidt.latinreader.layouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;

/**
 * Creates list items for the translation library listview.
 */
public class TranslationListViewAdapter extends LibraryListViewAdapter {

    public TranslationListViewAdapter(Context context, WorkInfo[] values) {
        super(context, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.library_listviewitem, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.listviewitem_title);
        title.setText(values[position].getEnglishTitle());
        TextView author = (TextView) rowView.findViewById(R.id.listviewitem_author);
        author.setText(values[position].getEnglishAuthor());

        return rowView;
    }
}
