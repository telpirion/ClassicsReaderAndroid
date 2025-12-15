@file:Suppress("DEPRECATION")

package com.ericmschmidt.classicsreader.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.TypedValue
import android.view.*
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.ericmschmidt.classicsreader.MyApplication
import com.ericmschmidt.classicsreader.R as CoreR
import com.ericmschmidt.classicsreader.views.R
import com.ericmschmidt.classicsreader.data.ReadingViewModel
import com.ericmschmidt.classicsreader.data.WorkInfo.WorkType

/**
 * Displays the text of a work (source or translation).
 * <br></br>
 * This class is used for presenting both foreign language and English texts.
 * <br></br>
 * Layout files:
 * - res/layout/fragment_reading.xml
 * <br></br>
 * @author Eric Schmidt
 * @author <a href="https://telpirion.com">...</a>
 * @version 2.0
 * @since 1.0
 */
@Suppress("DEPRECATION")
class ReadingFragment : Fragment() {

    private var workToGetId: String? = null
    private var isTranslation = false
    private lateinit var viewModel: ReadingViewModel
    private var bookNum = 0
    private var lineNum = 0
    private var onPrevNextClickListener: OnClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use safeArgs.
        arguments?.let {
            val args = ReadingFragmentArgs.fromBundle(it)
            workToGetId = args.workId
            isTranslation = args.isTranslation
            bookNum = args.book
            lineNum = args.line
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reading, container, false)
    }

    /**
     * Loads the fragment and the associated ReadingViewModel.
     * @param savedInstanceState Bundle
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val readingPane = view.findViewById<TextView>(R.id.reading_surface)

        if (workToGetId.isNullOrEmpty()) {
            readingPane.text = resources.getString(CoreR.string.reading_no_book_open)
            return
        }

        val library = MyApplication.applicationInstance().library
        val work = library.getWorkInfoByID(workToGetId)
        var numLines = 1

        // Register the context menu
        registerForContextMenu(readingPane)

        // Set text size.
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        val textSize = sharedPreferences.getString(
            SettingsFragment.TEXT_SIZE,
            SettingsFragment.TEXT_SIZE_DEFAULT
        )
        readingPane.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize!!.toFloat())

        // Register this as the most recently read book.
        sharedPreferences.edit {
            putString(
                RECENTLY_READ,
                String.format("%s;%s", workToGetId, isTranslation)
            )
        }

        // After parsing the XML, the app presents poetry lines one at a time.
        // The user can override the number of lines to show per page.
        // This setting doesn't matter for prose, since one "line" equals one paragraph.
        if (work?.workType == WorkType.POEM) {
            val linesPerPage = sharedPreferences.getString(
                SettingsFragment.POEM_LINES,
                SettingsFragment.POEM_LINES_DEFAULT
            )
            numLines = linesPerPage!!.toInt()
        }

        if (work != null) {
            viewModel = ReadingViewModel(work, isTranslation, numLines)
        }

        if (bookNum >= 0) {
            viewModel.setCurrentBook(bookNum)
            viewModel.setCurrentLine(lineNum)
        }
        updateReadingSurface()

        // Determine whether to show the next/previous page controls and
        // add button click listeners.
        val showPageControls = sharedPreferences.getBoolean(
            SettingsFragment.SHOW_PAGE_CONTROLS,
            SettingsFragment.SHOW_PAGE_CONTROLS_DEFAULT
        )
        if (!showPageControls) {
            val buttonBar: ConstraintLayout = view.findViewById(R.id.reading_next_prev)
            buttonBar.visibility = View.GONE
        } else {
            val prevButton: ImageButton = view.findViewById(R.id.btn_prev_page)
            val nextButton: ImageButton = view.findViewById(R.id.btn_next_page)

            onPrevNextClickListener = OnClickListener { v ->
                if (v.id == R.id.btn_prev_page) {
                    viewModel.goToPage(false)
                } else {
                    viewModel.goToPage(true)
                }
                updateReadingSurface()
            }

            prevButton.setOnClickListener(onPrevNextClickListener)
            nextButton.setOnClickListener(onPrevNextClickListener)
        }

        /*
            Set touch responses:
            - touch the left side of the reading area, go back a page.
            - touch the right side of the reading area, go forward a page.
            - touch the middle of the reading area, either scroll or bring up context menu.
        */
        readingPane.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val viewWidth = v.width
                val eventX = event.x
                val hitArea = viewWidth / HIT_AREA_RATIO

                // the user has touched an edge; flip the page.
                if (eventX < hitArea || eventX > viewWidth - hitArea) {
                    if (eventX > viewWidth / 2) {
                        viewModel.goToPage(true)
                    } else viewModel.goToPage(false)
                    updateReadingSurface()
                } else { // The user touched the middle of the screen.
                    readingPane.performClick()
                }
                return@setOnTouchListener true
            }
            true
        }

        readingPane.setOnClickListener { v -> activity?.openContextMenu(v) }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu, v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val menuLabel = if (isTranslation)
            CoreR.string.context_menu_source
        else
            CoreR.string.context_menu_translation

        menu.add(0, MENU_SWITCH_VIEW, 0, menuLabel)

        if (this.viewModel.toc.isNotEmpty())
            menu.add(0, MENU_VIEW_TOC, 1, CoreR.string.context_menu_toc)
    }

    // Switch views, translation to/from source
    override fun onContextItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        val navController = NavHostFragment.findNavController(this)

        when (id) {
            // In this case, the fragment navigates to itself
            MENU_SWITCH_VIEW -> {
                val args = ReadingFragmentArgs(
                    workId = workToGetId as String,
                    isTranslation = !isTranslation,
                    book = viewModel.currentBookIndex,
                    line = viewModel.currentLineIndex)
                navController.navigate(R.id.reading_dest, args.toBundle())
                return true
            }
            MENU_VIEW_TOC -> {
                val action =
                    ReadingFragmentDirections.actionReadingDestToTocDest(
                        workId = this.workToGetId!!,
                        isTranslation = this.isTranslation)
                navController.navigate(action)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    // Change the text on the page by advancing the reading position.
    private fun updateReadingSurface() {
        val readingPane = view?.findViewById<TextView>(R.id.reading_surface)
        val readingInfo = view?.findViewById<TextView>(R.id.reading_info)
        val readingPosition = view?.findViewById<TextView>(R.id.reading_position)

        readingPane?.text = viewModel.getCurrentPage()
        readingInfo?.text = viewModel.getReadingInfo()
        readingPosition?.text = viewModel.getReadingPositionString()
    }

    companion object {
        const val HIT_AREA_RATIO = 4
        const val RECENTLY_READ = "recently_read"

        private const val MENU_SWITCH_VIEW = 0
        private const val MENU_VIEW_TOC = 1
    }
}
