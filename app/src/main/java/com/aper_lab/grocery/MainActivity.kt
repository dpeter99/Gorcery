package com.aper_lab.grocery


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.database.ScrapperDatabaseInterface
import com.aper_lab.grocery.fragment.addrecipe.AddRecipeFragmentDirections
import com.aper_lab.scraperlib.RecipeAPIService
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IFABProvider {

    lateinit var navController : NavController;

    lateinit var  appBarConfiguration : AppBarConfiguration;

    lateinit var drawerLayout: DrawerLayout;

    var fabContext : IHasFAB? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RecipeAPIService.initApi(ScrapperDatabaseInterface);
        setupNavBar()

        val user = FirebaseAuth.getInstance().currentUser
        //checkSignedIn(user);
        FirebaseAuth.getInstance().addAuthStateListener {
            val user = it.currentUser
            //checkSignedIn(user);
        }

        when {
            intent?.action == Intent.ACTION_SEND -> {
                Log.e("TEST",intent.getStringExtra(Intent.EXTRA_TEXT)?:"");
                val bundle = bundleOf("url" to intent.getStringExtra(Intent.EXTRA_TEXT))
                navController.navigate(R.id.addRecipeFragment, bundle);
            }
            else -> {
                // Handle other intents, such as being started from the home screen
            }
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            bottom_bar?.performShow();
        }
    }

    public fun openCloseNavigationDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setupNavBar() {
        //val toolbar = findViewById<Toolbar>(R.id.app_toolbar)
        val bottomBar = findViewById<BottomAppBar>(R.id.bottom_bar)
        setSupportActionBar(bottomBar)

        //bottom_bar.replaceMenu(R.menu.main_menu)
        /*
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
        */


        navController = findNavController(R.id.myNavHostFragment);
        setupNavigationMenu(navController);

        fab.setOnClickListener {
            fabContext?.onFABClicked();
        }
    }

    private fun setupNavigationMenu(navController: NavController) {
        navController.setGraph(R.navigation.navigation);

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout);

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
            fab?.hide();
        }
        else {


            fab?.show();
            fab?.setImageResource(props.icon);
            bottom_bar?.fabAlignmentMode = props.position;
        }
    }

    override fun setFABListener(a: IHasFAB) {
        fabContext = a;
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }

}
