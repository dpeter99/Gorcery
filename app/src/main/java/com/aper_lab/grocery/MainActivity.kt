package com.aper_lab.grocery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.scraperlib.RecipeAPIService
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        val nav = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout);
        val appToolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_toolbar);

        val navController = this.findNavController(R.id.myNavHostFragment)

        appToolbar.setupWithNavController(navController,nav);
        */

        RecipeAPIService.initApi(RecipeDatabase);

        val navController = findNavController(R.id.myNavHostFragment)
        navController.setGraph(R.navigation.navigation);

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val appToolbar = findViewById<Toolbar>(R.id.app_toolbar)


        val appBarConfiguration = AppBarConfiguration(navController.graph, drawer).

        NavigationUI.setupWithNavController(appToolbar, navController, drawer)
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
    }

    private fun setupNavigationMenu(navController: NavController) {
        val sideNavView = findViewById<NavigationView>(R.id.navigation_view)
        sideNavView?.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }

}
