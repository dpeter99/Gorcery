package com.aper_lab.grocery


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.scraperlib.RecipeAPIService
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IFABProvider {

    lateinit var  appBarConfiguration : AppBarConfiguration;

    var fabContext : IHasFAB? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RecipeAPIService.initApi(RecipeDatabase);

        //val toolbar = findViewById<Toolbar>(R.id.app_toolbar)
        val bottomBar = findViewById<BottomAppBar>(R.id.bottom_bar)
        setSupportActionBar(bottomBar)
        bottom_bar.replaceMenu(R.menu.main_menu)
        bottom_bar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.recipe_list -> {
                    Toast.makeText(this, "Clicked menu item 1", Toast.LENGTH_SHORT).show()

                    true
                }
                R.id.discover_recipes -> {
                    Toast.makeText(this, "Clicked menu item 2", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        val navController = findNavController(R.id.myNavHostFragment);
        setupNavigationMenu(navController);

        fab.setOnClickListener {
            fabContext?.onFABClicked();
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bottom_app_bar, menu)
        return true
    }

    private fun setupNavigationMenu(navController: NavController) {
        navController.setGraph(R.navigation.navigation);

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout);

        val navigationView = findViewById<NavigationView>(R.id.navigation_view);

        appBarConfiguration = AppBarConfiguration(
                setOf(R.id.discover_recipes, R.id.recipe_list),
                drawerLayout)

        navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration);
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp(appBarConfiguration)
    }

    override fun setFABProperties(props: FABParameters?) {
        if(props == null){
            fab.hide();
        }
        else {
            fab.show();
            fab.setImageResource(props.icon);
            bottom_bar.fabAlignmentMode = props.position;
        }
    }

    override fun setFABListener(a: IHasFAB) {
        fabContext = a;
    }
}
