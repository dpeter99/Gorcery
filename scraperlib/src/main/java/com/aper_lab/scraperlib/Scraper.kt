package com.aper_lab.scraperlib;

import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.scrapers.*
import java.net.URL

class Scraper {
    var scrapers: MutableMap<String, RecipeScraper> = mutableMapOf();

    init {
        scrapers["www.allrecipes.com"] =    Allrecipes();
        scrapers["www.nosalty.hu"] =        Nosalty();
        scrapers["www.delish.com"] =        Delish();
        scrapers["www.mindmegette.hu"] =    Mindmegette();
        scrapers["tasty.co"] =              Tasty();
    }

    fun scrape(path: String): Recipe?{
        var url = URL(path);
        val rec = scrapers[url.host]?.scrapFromLink(path);
        println("-------------------------------------------")
        println(rec.toString());
        return rec;
    }
}