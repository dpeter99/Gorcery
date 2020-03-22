package com.aper_lab.scraperlib

import com.aper_lab.scraperlib.api.DatabaseConnection
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.datastore.DataStore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async

object RecipeAPIService {

    val scraper = Scraper();

    val dataStore = DataStore();

    val scope = MainScope();

    fun InitApi(db: DatabaseConnection){
        dataStore.Init(db);
    }

    fun getRecipeFromURLAsync(url:String): Deferred<Recipe?> {
        return GlobalScope.async{

            var rec = dataStore.getRecipebyURL(url);
            if(rec == null) {
                rec = scraper.scrape(url);
                if (rec != null) {
                    dataStore.addRecipe(rec);
                }
            }
            rec;
        };
    }


    fun getRecipeByIDAsync(id: String): Deferred<Recipe?>{
        return scope.async{

            var rec = dataStore.getRecipebyID(id);
            if(rec == null) {
                rec = Recipe();
            }
            rec;
        };
    }
}