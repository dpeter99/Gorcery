package com.aper_lab.scraperlib

import com.aper_lab.scraperlib.data.Recipe
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

object RecipeAPIService {

    val scraper = Scraper();

    fun GetRecipe(url:String): Deferred<Recipe?> {
        return GlobalScope.async{ scraper.scrape(url)};
    }

}