package com.ericmschmidt.latinreader.activities;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.latinreader.exceptions.ForceCloseHandler;
import com.ericmschmidt.latinreader.fragments.DictionaryFragment;
import com.ericmschmidt.latinreader.fragments.LibraryFragment;
import com.ericmschmidt.latinreader.fragments.ReadingFragment;
import com.ericmschmidt.latinreader.fragments.SettingsFragment;
import com.ericmschmidt.latinreader.fragments.VocabularyFragment;

import static com.ericmschmidt.latinreader.fragments.ReadingFragment.TRANSLATION_FLAG;
import static com.ericmschmidt.latinreader.fragments.ReadingFragment.WORKTOGET;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LibraryFragment.OnLibraryListViewClick,
        ReadingFragment.OnReadingViewSwitch {

    private String currentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ForceCloseHandler(this));

        setContentView(R.layout.activity_main);

        // Hide the system bar.
        //toggleSystemTray(View.SYSTEM_UI_FLAG_FULLSCREEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Apply the current icon to the nav bar.
        try {
            String applicationName = getApplicationContext().getPackageName();
            View headerView = navigationView.getHeaderView(0);
            ImageView iconHolder = (ImageView)headerView.findViewById(R.id.icon_image);
            Drawable icon = getPackageManager().getApplicationIcon(applicationName);
            iconHolder.setImageDrawable(icon);
        } catch (Exception e) {
            MyApplication.logError(this.getClass(), e.getMessage());
        }

        // Need to load first fragment (library view) into activity.
        swapFragments(new LibraryFragment(), false);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            swapFragments(new SettingsFragment(), true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
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
                fragment = ReadingFragment.newInstance(workIdData[0], workIdData[1]);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onLibraryListViewClick(String workId, String isTranslation) {
        swapFragments(ReadingFragment.newInstance(workId, isTranslation), true);
    }

    public void onReadingViewSwitch(String workId, String isTranslation) {
        swapFragments(ReadingFragment.newInstance(workId, isTranslation), true);
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
                fragmentTransaction.replace(R.id.fragment_container, fragment, currentFragmentTag);
            else
                fragmentTransaction.add(R.id.fragment_container, fragment, currentFragmentTag);

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
}
