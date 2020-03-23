package com.aper_lab.grocery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.scraperlib.RecipeAPIService

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

        RecipeAPIService.InitApi(RecipeDatabase);

        val navController = findNavController(R.id.myNavHostFragment)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val appToolbar = findViewById<Toolbar>(R.id.app_toolbar)
        navController.setGraph(R.navigation.navigation);
        NavigationUI.setupWithNavController(appToolbar, navController, drawer)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }

}
