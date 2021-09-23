package com.ericmschmidt.latinreader.layouts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;

/**Subclass of RecyclerViewAdapter.
 * Used for displaying works in the LibraryFragment's RecyclerView.
 *
 * Has two nested types:
 * - class ViewHolder (for holding individual items in the RecyclerView)
 * - interface Listener (for responding to clicks in the RecyclerView)
 *
 * Layout files:
 * - res/layout/cardviewitem_libraryrecyclerview.xml
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.4
 */
public class LibraryRecyclerViewAdapter extends
        RecyclerView.Adapter<LibraryRecyclerViewAdapter.ViewHolder> {

    protected final WorkInfo[] works;
    protected final boolean isTranslation;
    private Listener listener;


    /**
     * Constructor for class
     * @param works the collection of works to display in the RecyclerView
     * @param isTranslation whether the list is English translations.
     */
    public LibraryRecyclerViewAdapter(WorkInfo[] works, boolean isTranslation) {
        this.works = works;
        this.isTranslation = isTranslation;
    }

    @Override
    public LibraryRecyclerViewAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardviewitem_libraryrecyclerview, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    @SuppressWarnings("all") // TODO(telpirion): Switch position determination.
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.layout;
        WorkInfo info = works[position];
        String title = info.getTitle();
        String author = info.getAuthor();

        if (this.isTranslation) {
            title = info.getEnglishTitle();
            author = info.getEnglishAuthor();
        }

        TextView titleView = (TextView) cardView.findViewById(R.id.listviewitem_title);
        titleView.setText(title);
        TextView authorView = (TextView) cardView.findViewById(R.id.listviewitem_author);
        authorView.setText(author);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return works.length;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView layout;

        public ViewHolder(CardView v) {
            super(v);
            layout = v;
        }
    }

}
