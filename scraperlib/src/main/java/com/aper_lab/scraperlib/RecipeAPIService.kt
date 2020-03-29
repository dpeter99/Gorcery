package com.aper_lab.scraperlib

import com.aper_lab.scraperlib.api.DatabaseConnection
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.datastore.DataStore
import com.aper_lab.scraperlib.scrapers.*
import com.aper_lab.scraperlib.util.HashUtils
import com.aper_lab.scraperlib.util.URLutils
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import java.net.URL

object RecipeAPIService {

    //val scraper = Scraper();

    var registry = ScraperRegistry();

    val dataStore = DataStore();

    val scope = MainScope();

    init {
        Allrecipes.Register(registry);
        Delish.Register(registry);
        Mindmegette.Register(registry);
        Nosalty.Register(registry);
        Tasty.Register(registry);
    }

    fun initApi(db: DatabaseConnection){
        dataStore.Init(db);
    }

    fun getRecipeFromURLAsync(url:String): Deferred<Recipe?> {
        return GlobalScope.async{

            var rec = dataStore.getRecipebyURL(url);
            if(rec == null) {
                rec = scrape(url);
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

    fun getSourceIDfromURL(url:String): String{
        val link = URL(url);
        return registry.mapping[link.host]?.getSourceID()?: "";
    }

    fun scrape(path: String): Recipe?{
        val url_parsed = URLutils.HTTPToHTTPS(path);
        var url = URL(url_parsed);

        val rec = registry.mapping[url.host]?.scrapFromLink(url);
        if(rec != null) {
            rec.id = HashUtils.md5(rec.name);
            println("-------------------------------------------")
            println(rec.toString());
        }





        return rec;
    }
}