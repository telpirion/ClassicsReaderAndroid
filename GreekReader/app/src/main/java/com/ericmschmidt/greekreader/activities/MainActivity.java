package com.ericmschmidt.greekreader.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.ericmschmidt.greekreader.MyApplication;
import com.ericmschmidt.greekreader.R;
import com.ericmschmidt.greekreader.exceptions.ForceCloseHandler;
import com.ericmschmidt.greekreader.fragments.DictionaryFragment;
import com.ericmschmidt.greekreader.fragments.LibraryFragment;
import com.ericmschmidt.greekreader.fragments.ReadingFragment;
import com.ericmschmidt.greekreader.fragments.SettingsFragment;
import com.ericmschmidt.greekreader.fragments.VocabularyFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LibraryFragment.OnLibraryListViewClick,
        ReadingFragment.OnReadingViewSwitch {

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

        // Need to load first fragment (library view) into activity.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LibraryFragment libFragment = new LibraryFragment();
        fragmentTransaction.add(R.id.fragment_container, libFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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
                swapFragments(fragment);

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

            Fragment fragment = new SettingsFragment();
            swapFragments(fragment);
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

        swapFragments(fragment);

        // Close drawer animation.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onLibraryListViewClick(String workId, String isTranslation) {
        createReadingView(workId,  isTranslation);
    }

    public void onReadingViewSwitch(String workId, String isTranslation) {
        createReadingView(workId, isTranslation);
    }

    private String getRecentlyRead() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPrefs.getString(ReadingFragment.RECENTLY_READ, "");
    }

    private void createReadingView(String workId, String isTranslation) {
        ReadingFragment fragment = ReadingFragment.newInstance(workId, isTranslation);
        swapFragments(fragment);
    }

    private void swapFragments(Fragment fragment) {

        try {
            // Need to load first fragment into activity.
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container, fragment);
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
