package com.ericmschmidt.latinreader.activities;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.latinreader.exceptions.ForceCloseHandler;
import com.ericmschmidt.latinreader.fragments.DictionaryFragment;
import com.ericmschmidt.latinreader.fragments.LibraryFragment;
import com.ericmschmidt.latinreader.fragments.LibraryFragmentDirections;
import com.ericmschmidt.latinreader.fragments.ReadingFragment;
import com.ericmschmidt.latinreader.fragments.SettingsFragment;
import com.ericmschmidt.latinreader.fragments.TOCFragment;
import com.ericmschmidt.latinreader.fragments.VocabularyFragment;
import com.google.android.material.navigation.NavigationView;

/** Base activity for this app.
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LibraryFragment.OnLibraryListViewClick,
        ReadingFragment.OnReadingViewSwitch,
        ReadingFragment.OnViewTOCClick,
        TOCFragment.OnTOCListViewClick {

    private String currentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ForceCloseHandler(this));

        setContentView(R.layout.activity_main);

        // Hide the system bar.
        //toggleSystemTray(View.SYSTEM_UI_FLAG_FULLSCREEN);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        NavHostFragment navHostFragment =
                (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // Apply the current icon to the nav bar.
        try {
            String applicationName = getApplicationContext().getPackageName();
            Drawable icon = getPackageManager().getApplicationIcon(applicationName);
        } catch (Exception e) {
            MyApplication.logError(this.getClass(), e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        SearchView searchView = (SearchView) menu.findItem(R.id.action_dictionary).getActionView();

        String queryHint = getString(R.string.dictionary_query_hint_short);
        searchView.setQueryHint(queryHint);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                DictionaryFragment fragment = DictionaryFragment.newInstance(query);
                swapFragments(fragment, true);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            swapFragments(new SettingsFragment(), true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_recent) {
            // Remember to store recently read as workId;isTranslation.
            String workId = getRecentlyRead();
            if (!workId.equals("")) {
                String[] workIdData = workId.split(";");
                boolean isTranslation = Boolean.getBoolean(workIdData[1]);
                fragment = ReadingFragment.newInstance(workIdData[0], isTranslation);
            }
            else {
                fragment = new ReadingFragment();
            }
        }

        else if (id == R.id.nav_library) {
            fragment = new LibraryFragment();

        } else if (id == R.id.nav_translation) {

            // Pass a flag to the library Fragment to let it
            // know that we want to show the translations.
            fragment = LibraryFragment.newInstance("true");

        } else if (id == R.id.nav_dictionary) {
            fragment = new DictionaryFragment();

        } else if (id == R.id.nav_vocab) {
            fragment = new VocabularyFragment();

        } else if (id == R.id.nav_settings) {
            fragment = new SettingsFragment();
        }

        swapFragments(fragment, true);

        // Close drawer animation.
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onLibraryListViewClick(String workId, boolean isTranslation) {
        swapFragments(ReadingFragment.newInstance(workId, isTranslation), true);
    }

    public void onReadingViewSwitch(ReadingFragment.ReadingViewOptions options) {
        swapFragments(ReadingFragment.newInstance(options), true);
    }

    private String getRecentlyRead() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPrefs.getString(ReadingFragment.RECENTLY_READ, "");
    }

    // Replace (or add) fragments to the fragment container in the activity.
    private void swapFragments(Fragment fragment, boolean isReplace) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Track fragments on backstack using tags.
            currentFragmentTag = fragment.getClass().getName();

            if (isReplace)
                fragmentTransaction.replace(R.id.nav_host_fragment, fragment, currentFragmentTag);
            else
                fragmentTransaction.add(R.id.nav_host_fragment, fragment, currentFragmentTag);

            fragmentTransaction.addToBackStack(fragment.toString());
            fragmentTransaction.commit();
        } catch (Exception ex) {
            MyApplication.logError(this.getClass(), ex.getMessage());
        }
    }

    private void toggleSystemTray(int uiOptions){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onViewTOC(TOCFragment.TOCViewOptions options) {
        swapFragments(TOCFragment.newInstance(options), true);
    }

    @Override
    public void onTOCListViewClick(ReadingFragment.ReadingViewOptions options) {
        swapFragments(ReadingFragment.newInstance(options), true);
    }
}
