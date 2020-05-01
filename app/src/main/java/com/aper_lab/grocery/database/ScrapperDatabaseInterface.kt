package com.aper_lab.grocery.database

import com.aper_lab.grocery.User
import com.aper_lab.scraperlib.api.DatabaseConnection
import com.aper_lab.scraperlib.api.IHasID
import com.aper_lab.scraperlib.data.Recipe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object ScrapperDatabaseInterface: DatabaseConnection {

    override fun storeData(path: String, data: IHasID) {
        TODO("Deprecated")
    }

    override suspend fun getRecipeByURL(url: String): Recipe? {
        return RecipeDatabase.getRecipeByURL(url);
    }

    override suspend fun getRecipeByID(id: String): Recipe? {
        return RecipeDatabase.getRecipeByID(id);
    }

    override fun updateRecipe(recipe: Recipe) {
        GlobalScope.launch {
            User.getInstance().addNewRecipe(recipe);
        }
        //return RecipeDatabase.updateRecipe(recipe);
    }

    override fun storeRecipe(recipe: Recipe) {
        GlobalScope.launch {
            //Ask the user to store the recipe as it can add the user_id to it
            User.getInstance().addNewRecipe(recipe);
        }
    }
}