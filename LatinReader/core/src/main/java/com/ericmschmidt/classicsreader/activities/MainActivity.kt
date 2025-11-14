package com.ericmschmidt.classicsreader.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ericmschmidt.classicsreader.R
import com.ericmschmidt.classicsreader.databinding.ActivityMainBinding
import com.ericmschmidt.classicsreader.exceptions.ForceCloseHandler
import com.ericmschmidt.classicsreader.logError
import com.ericmschmidt.classicsreader.ui.fragments.DictionaryFragmentArgs
import com.ericmschmidt.classicsreader.ui.fragments.LibraryFragmentArgs
import com.ericmschmidt.classicsreader.ui.fragments.ReadingFragment

/**
 * Base activity for this app.
 * @author Eric Schmidt
 * @author <a href="http://telpirion.com">...</a>
 * @version 1.5
 * @since 1.0
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.setDefaultUncaughtExceptionHandler(ForceCloseHandler(this))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.libraryFragment, R.id.reading_dest, R.id.dictionary_dest,
                R.id.vocab_dest, R.id.settings_dest, R.id.help_dest, R.id.info_dest
            ), binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_translation -> {
                    val args = LibraryFragmentArgs.Builder()
                        .setIsTranslations(true)
                        .build()
                    navController.navigate(R.id.libraryFragment, args.toBundle())
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> {
                    // Let the NavController handle other menu items
                    menuItem.isChecked = true
                    navController.navigate(menuItem.itemId)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    // If we are not in the start destination, we let the NavController handle it.
                    if (navController.currentDestination?.id != R.id.libraryFragment) {
                        navController.popBackStack()
                    } else {
                        // Otherwise, we finish the activity.
                        finish()
                    }
                }
            }
        })

        // Apply the current icon to the nav bar.
        try {
            packageManager.getApplicationIcon(applicationContext.packageName)
        } catch (e: Exception) {
            logError(this.javaClass, e.message)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val searchItem = menu.findItem(R.id.action_dictionary)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = getString(R.string.dictionary_query_hint_short)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val args = DictionaryFragmentArgs.Builder()
                    .setDictionaryQuery(query)
                    .build()
                navController.navigate(R.id.dictionary_dest, args.toBundle())
                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                navController.navigate(R.id.settings_dest)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    private fun getRecentlyRead(): String {
        val sharedPrefs = getSharedPreferences(packageName + "_preferences", MODE_PRIVATE)
        return sharedPrefs.getString(ReadingFragment.RECENTLY_READ, "") ?: ""
    }
}
