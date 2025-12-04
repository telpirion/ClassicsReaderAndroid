package com.ericmschmidt.classicsreader.ui.layouts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ericmschmidt.classicsreader.views.databinding.TocListviewitemBinding
import com.ericmschmidt.classicsreader.datamodel.TOCEntry

/**
 * Subclass of ArrayAdapter, used for displaying chapters in the TOCFragment.
 *
 * Layout files:
 * - res/layout/toc_item.xml
 *
 * @author Eric Schmidt
 * @author <a href="http://telpirion.com">...</a>
 * @version 2.0
 * @since 1.4
 */
class TOCListViewAdapter(context: Context, private val values: Array<TOCEntry>) :
    ArrayAdapter<TOCEntry>(context, -1, values) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            TocListviewitemBinding.inflate(inflater, parent, false)
        } else {
            TocListviewitemBinding.bind(convertView)
        }

        binding.tocitemEntry.text = values[position].toString()

        return binding.root
    }
}
