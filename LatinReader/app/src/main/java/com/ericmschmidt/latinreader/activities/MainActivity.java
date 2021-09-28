package com.ericmschmidt.latinreader.activities;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
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
import androidx.navigation.fragment.NavHostFragment;

import com.ericmschmidt.latinreader.MyApplication;
import com.ericmschmidt.classicsreader.R;
import com.ericmschmidt.latinreader.exceptions.ForceCloseHandler;
import com.ericmschmidt.latinreader.fragments.DictionaryFragmentArgs;
import com.ericmschmidt.latinreader.fragments.LibraryFragment;
import com.ericmschmidt.latinreader.fragments.LibraryFragmentArgs;
import com.ericmschmidt.latinreader.fragments.ReadingFragment;
import com.ericmschmidt.latinreader.fragments.ReadingFragmentArgs;
import com.ericmschmidt.latinreader.fragments.TOCFragment;
import com.ericmschmidt.latinreader.fragments.TOCFragmentArgs;
import com.google.android.material.navigation.NavigationView;

/** Base activity for this app.
 * @author Eric Schmidt
 * @author http://telpirion.com
 * @version 1.5
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ForceCloseHandler(this));

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
                DictionaryFragmentArgs args = new DictionaryFragmentArgs.Builder()
                        .setDictionaryQuery(query)
                        .build();
                swapFragments(R.id.dictionary_dest, args.toBundle());
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
            swapFragments(R.id.settings_dest, null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Close drawer animation.
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_recent) {
            // Remember to store recently read as workId;isTranslation.
            String workId = getRecentlyRead();
            if (!workId.equals("")) {
                String[] workIdData = workId.split(";");
                boolean isTranslation = Boolean.getBoolean(workIdData[1]);

                ReadingFragmentArgs args = new ReadingFragmentArgs.Builder()
                        .setIsTranslation(isTranslation)
                        .setWorkId(workIdData[0])
                        .build();

                swapFragments(R.id.reading_dest, args.toBundle());

            }
            else {
                swapFragments(R.id.reading_dest, null);
            }
        }

        else if (id == R.id.nav_library) {
            swapFragments(R.id.libraryFragment, null);

        } else if (id == R.id.nav_translation) {

            // Pass a flag to the library Fragment to let it
            // know that we want to show the translations.
            LibraryFragmentArgs args = new LibraryFragmentArgs.Builder()
                    .setIsTranslations(true)
                    .build();

            swapFragments(R.id.libraryFragment, args.toBundle());

        } else if (id == R.id.nav_dictionary) {
            swapFragments(R.id.dictionary_dest, null);

        } else if (id == R.id.nav_vocab) {
            swapFragments(R.id.vocab_dest, null);

        } else if (id == R.id.nav_settings) {
            swapFragments(R.id.settings_dest, null);
        }

        return true;
    }

    private String getRecentlyRead() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPrefs.getString(ReadingFragment.RECENTLY_READ, "");
    }

    // Use nav controller and nav graph to navigate to different fragments.
    private void swapFragments(int resourceId, @Nullable Bundle args) {
        FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        NavHostFragment navHostFragment =
                (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        if (args != null) {
            navController.navigate(resourceId, args);
        } else {
            navController.navigate(resourceId);
        }
    }
}
