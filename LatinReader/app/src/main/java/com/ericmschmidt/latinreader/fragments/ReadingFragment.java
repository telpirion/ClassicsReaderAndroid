package com.ericmschmidt.latinreader.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.datamodel.Library;
import com.ericmschmidt.latinreader.datamodel.Manifest;
import com.ericmschmidt.latinreader.datamodel.ReadingViewModel;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;
import com.ericmschmidt.classicsreader.R;

/** Displays the text of a work (source or translation).
 *
 *  This class is used for presenting both foreign language and English texts.
 *
 *  Layout files:
 *  - res/layout/fragment_reading.xml
 *
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
public class ReadingFragment extends Fragment {
    public static final int HIT_AREA_RATIO = 4;
    public static final String RECENTLY_READ = "recently_read";
    public static final String TAG = "ReadingFragment";

    private final int MENU_SWITCH_VIEW = 0;
    private final int MENU_VIEW_TOC = 1;

    private String workToGetId;
    private boolean isTranslation;
    private ReadingViewModel viewModel;
    private int bookNum;
    private int lineNum;
    private OnClickListener mOnPrevNextClickListener;

    /**
     * Required empty public constructor
     */
    public ReadingFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use safeArgs.
        if (getArguments() != null) {
            ReadingFragmentArgs args = ReadingFragmentArgs.fromBundle(getArguments());
            workToGetId = args.getWorkId();
            isTranslation = args.getIsTranslation();
            bookNum = args.getBook();
            lineNum = args.getLine();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reading, container, false);
    }

    /**
     * Loads the fragment and the associated ReadingViewModel.
     * @param onSavedInstanceState Bundle
     */
    @SuppressLint("ClickableViewAccessibility")
    public void onActivityCreated(Bundle onSavedInstanceState) {

        super.onActivityCreated(onSavedInstanceState);

        final TextView readingPane = (TextView)getView().findViewById(R.id.reading_surface);

        if (workToGetId == null || workToGetId.equals("")) {
            readingPane.setText(getResources().getString(R.string.reading_no_book_open));
            return;
        }

        Manifest manifest = MyApplication.getManifest();
        Library library = new Library(manifest.getCollection());
        WorkInfo work = library.getWorkInfoByID(workToGetId);
        int numLines = 1;

        // Register the context menu
        registerForContextMenu(readingPane);

        // Set text size.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String textSize = sharedPreferences.getString(SettingsFragment.TEXT_SIZE,
            SettingsFragment.TEXT_SIZE_DEFAULT);
        readingPane.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Float.parseFloat(textSize));

        // Register this as the most recently read book.
        sharedPreferences.edit()
            .putString(RECENTLY_READ, String.format("%s;%s",workToGetId,
                Boolean.toString(isTranslation)))
            .apply();

        // After parsing the XML, the app presents poetry lines one at a time.
        // The user can override the number of lines to show per page.
        // This setting doesn't matter for prose, since one "line" equals one paragraph.
        if (work.getWorkType() == WorkInfo.WorkType.POEM) {
            String linesPerPage = sharedPreferences.getString(SettingsFragment.POEM_LINES,
                SettingsFragment.POEM_LINES_DEFAULT);
            numLines = Integer.parseInt(linesPerPage);
        }

        viewModel = new ReadingViewModel(work, isTranslation, numLines);

        if (bookNum >= 0) {
            viewModel.setCurrentBook(bookNum);
            viewModel.setCurrentLine(lineNum);
        }
        updateReadingSurface();

        // Determine whether to show the next/previous page controls and
        // add button click listeners.
        boolean showPageControls = sharedPreferences.getBoolean(SettingsFragment.SHOW_PAGE_CONTROLS,
            SettingsFragment.SHOW_PAGE_CONTROLS_DEFAULT);
        if (!showPageControls) {
            ConstraintLayout buttonBar = getView().findViewById(R.id.reading_next_prev);
            buttonBar.setVisibility(View.GONE);
        } else {
            ImageButton prevButton = getView().findViewById(R.id.btn_prev_page);
            ImageButton nextButton = getView().findViewById(R.id.btn_next_page);

            mOnPrevNextClickListener = new OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Just in case the viewModel isn't populated yet.
                    if (viewModel == null) {
                        return;
                    }

                    if (view.getId() == R.id.btn_prev_page) {
                        viewModel.goToPage(false);
                    } else {
                        viewModel.goToPage(true);
                    }

                    updateReadingSurface();
                }
            };

            prevButton.setOnClickListener(mOnPrevNextClickListener);
            nextButton.setOnClickListener(mOnPrevNextClickListener);
        }


        /*
            Set touch responses:
            - touch the left side of the reading area, go back a page.
            - touch the right side of the reading area, go forward a page.
            - touch the middle of the reading area, either scroll or bring up context menu.
        */
        readingPane.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int viewWidth = v.getWidth();
                    float eventX = event.getX();
                    int hitArea = viewWidth / HIT_AREA_RATIO;

                    // the user has touched an edge; flip the page.
                    if ((eventX < hitArea) ||
                            (eventX > viewWidth - hitArea)) {

                        if (eventX > viewWidth / 2) {
                            viewModel.goToPage(true);
                        } else viewModel.goToPage(false);

                        updateReadingSurface();
                    } else { // The user touched the middle of the screen.
                        readingPane.performClick();
                    }

                    return true;
                }
                return true;
            }
        });

        readingPane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().openContextMenu(v);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        int menuLabel = isTranslation ?
                R.string.context_menu_source :
                R.string.context_menu_translation;

        menu.add(0, MENU_SWITCH_VIEW, 0, menuLabel);

        if (this.viewModel.getTOC().length > 0)
            menu.add(0, MENU_VIEW_TOC, 1, R.string.context_menu_toc);
    }

    // Switch views, translation to/from source
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();
        NavController navController = NavHostFragment.findNavController(this);

        switch (id) {
            // In this case, the fragment navigates to itself
            case MENU_SWITCH_VIEW:
                ReadingFragmentArgs args = new ReadingFragmentArgs.Builder()
                        .setWorkId(workToGetId)
                        .setIsTranslation(!isTranslation)
                        .setBook(viewModel.getCurrentBookIndex())
                        .setLine(viewModel.getCurrentLineIndex())
                        .build();
                navController.navigate(R.id.reading_dest, args.toBundle());
                return true;
            case MENU_VIEW_TOC:
                ReadingFragmentDirections.ActionReadingDestToTocDest action =
                        ReadingFragmentDirections.actionReadingDestToTocDest(this.workToGetId);
                action.setIsTranslation(this.isTranslation);
                navController.navigate(action);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    // Change the text on the page by advancing the reading position.
    private void updateReadingSurface() {

        TextView readingPane = (TextView)this.getView()
                .findViewById(R.id.reading_surface);
        TextView readingInfo = (TextView)this.getView()
                .findViewById(R.id.reading_info);
        TextView readingPosition = (TextView)this.getView()
                .findViewById(R.id.reading_position);

        readingPane.setText(viewModel.getCurrentPage());
        readingInfo.setText(viewModel.getReadingInfo());
        readingPosition.setText(viewModel.getReadingPositionString());
    }
}
