package com.ericmschmidt.classicsreader.layouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.classicsreader.datamodel.TOCEntry;

/**Subclass of ArrayAdapter.
 * Used for displaying chapters in the TOCFragment
 *
 * Layout files:
 * - res/layout/toc_item.xml
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.4
 */
public class TOCListViewAdapter extends ArrayAdapter<TOCEntry> {
    protected final Context context;
    protected final TOCEntry[] values;

    public TOCListViewAdapter(Context context, TOCEntry[] values) {
        super(context, -1, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.toc_listviewitem, parent, false);

        TextView entry = (TextView) rowView.findViewById(R.id.tocitem_entry);
        entry.setText(values[position].toString());

        return rowView;
    }
}
