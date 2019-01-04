package com.ericmschmidt.latinreader.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.latinreader.datamodel.Library;
import com.ericmschmidt.latinreader.datamodel.Manifest;
import com.ericmschmidt.latinreader.datamodel.ReadingViewModel;
import com.ericmschmidt.latinreader.datamodel.WorkInfo;
import com.ericmschmidt.classicsreader.R;

public class ReadingFragment extends Fragment {

    public static final String WORK_TO_GET = "work";
    public static final String TRANSLATION_KEY = "translation";
    public static final String BOOK_KEY = "book";
    public static final String LINE_KEY = "line";
    public static final int HIT_AREA_RATIO = 4;
    public static final String RECENTLY_READ = "recently_read";
    public static final String TAG = "ReadingFragment";

    private final int MENU_SWITCH_VIEW = 0;
    private final int MENU_VIEW_TOC = 1;

    private String workToGetId;
    private boolean translationFlag;
    private OnReadingViewSwitch onReadingViewSwitch;
    private OnViewTOCClick onViewTOCClick;
    private ReadingViewModel viewModel;
    private int bookNum;
    private int lineNum;

    public ReadingFragment() {
        // Required empty public constructor
    }

    public static ReadingFragment newInstance(String workId, boolean isTranslation) {
        ReadingFragment fragment = new ReadingFragment();
        Bundle args = new Bundle();
        args.putString(WORK_TO_GET, workId);
        args.putBoolean(TRANSLATION_KEY, isTranslation);
        fragment.setArguments(args);
        return fragment;
    }

    public static ReadingFragment newInstance(ReadingViewOptions options) {
        ReadingFragment fragment = new ReadingFragment();
        Bundle args = new Bundle();
        args.putString(WORK_TO_GET, options.workId);
        args.putBoolean(TRANSLATION_KEY, options.isTranslation);
        args.putInt(BOOK_KEY, options.book);
        args.putInt(LINE_KEY, options.line);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookNum = lineNum = -1;
        if (getArguments() != null) {
            workToGetId = getArguments().getString(WORK_TO_GET);
            translationFlag = getArguments().getBoolean(TRANSLATION_KEY);
        }

        if (getArguments() != null && getArguments().getInt(BOOK_KEY) >= 0) {
            bookNum = getArguments().getInt(BOOK_KEY);
            lineNum = getArguments().getInt(LINE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        String textSize = sharedPreferences.getString(SettingsFragment.TEXT_SIZE, SettingsFragment.TEXT_SIZE_DEFAULT);
        readingPane.setTextSize(TypedValue.COMPLEX_UNIT_DIP, Float.parseFloat(textSize));

        // Register this as the most recently read book.
        sharedPreferences.edit()
            .putString(RECENTLY_READ, String.format("%s;%s",workToGetId,Boolean.toString(translationFlag)))
            .apply();

        // After parsing the XML, the app presents poetry lines one at a time.
        // The user can override the number of lines to show per page.
        // This setting doesn't matter for prose, since one "line" equals one paragraph.
        if (work.getWorkType() == WorkInfo.WorkType.POEM) {
            String linesPerPage = sharedPreferences.getString(SettingsFragment.POEM_LINES, SettingsFragment.POEM_LINES_DEFAULT);
            numLines = Integer.parseInt(linesPerPage);
        }

        viewModel = new ReadingViewModel(work, translationFlag, numLines);

        if (bookNum >= 0) {
            viewModel.setCurrentBook(bookNum);
            viewModel.setCurrentLine(lineNum);
        }
        updateReadingSurface();

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnReadingViewSwitch) {
            onReadingViewSwitch = (OnReadingViewSwitch) context;
            onViewTOCClick = (OnViewTOCClick) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onReadingViewSwitch = null;
        onViewTOCClick = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        int menuLabel = translationFlag ?
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

        switch (id) {
            case MENU_SWITCH_VIEW:
                ReadingViewOptions readingViewOptions = new ReadingViewOptions();
                readingViewOptions.workId = workToGetId;
                readingViewOptions.isTranslation = !translationFlag;
                readingViewOptions.book = viewModel.getCurrentBookIndex();
                readingViewOptions.line = viewModel.getCurrentLineIndex();
                onReadingViewSwitch.onReadingViewSwitch(readingViewOptions);
                return true;
            case MENU_VIEW_TOC:
                TOCFragment.TOCViewOptions options = new TOCFragment.TOCViewOptions();
                options.isTranslation = this.translationFlag;
                options.workId = this.workToGetId;
                onViewTOCClick.onViewTOC(options);
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

    /**
     * Communicates reading view switch click to host activity.
     */
    public interface OnReadingViewSwitch {
        void onReadingViewSwitch(ReadingViewOptions options);
    }

    /**
     * Communicates TOC menu item click to host activity.
     */
    public interface OnViewTOCClick {
        void onViewTOC(TOCFragment.TOCViewOptions options);
    }

    /**
     * Small struct for passing data between fragments and activity.
     */
    public static class ReadingViewOptions {
        public boolean isTranslation;
        public String workId;
        public int book;
        public int line;
    }
}
