package com.aper_lab.grocery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.scraperlib.RecipeAPIService
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var  appBarConfiguration : AppBarConfiguration;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RecipeAPIService.initApi(RecipeDatabase);

        //val toolbar = findViewById<Toolbar>(R.id.app_toolbar)
        val bottomBar = findViewById<BottomAppBar>(R.id.bottom_bar)
        setSupportActionBar(bottomBar)

        val navController = findNavController(R.id.myNavHostFragment);
        setupNavigationMenu(navController);


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

}
