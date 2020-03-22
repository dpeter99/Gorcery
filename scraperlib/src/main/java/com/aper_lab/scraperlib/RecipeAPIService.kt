package com.aper_lab.scraperlib

import com.aper_lab.scraperlib.api.DatabaseConnection
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.datastore.DataStore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

object RecipeAPIService {

    val scraper = Scraper();

    val dataStore = DataStore();

    fun InitApi(db: DatabaseConnection){
        dataStore.Init(db);
    }

    fun GetRecipe(url:String): Deferred<Recipe?> {
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

}