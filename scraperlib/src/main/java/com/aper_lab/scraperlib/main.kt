package com.aper_lab.scraperlib

import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.scrapers.*
import org.jsoup.Jsoup
import java.io.Console
import java.net.URL
import java.nio.charset.Charset

fun main(args: Array<String>) {

    val sites = listOf<String>(
        "https://tasty.co/recipe/creamy-sausage-bolognese",
        "http://www.mindmegette.hu/rakott-teszta-daralt-hussal.recept/",
        "https://www.delish.com/cooking/recipe-ideas/a25239697/easy-turkey-tetrazzini-recipe/",
        "https://www.delish.com/cooking/recipe-ideas/a19665622/easy-chicken-fajitas-recipe/",
        "https://www.delish.com/cooking/recipe-ideas/recipes/a55510/easy-baked-spaghetti-recipe/",
        "https://www.delish.com/cooking/recipe-ideas/a48247/cilantro-lime-chicken-recipe/",
        "https://www.nosalty.hu/recept/aranygaluska-ahogy-nagymamam-kesziti",
        "https://www.nosalty.hu/recept/tejszines-sonkas-penne"
        )

    var scraper = Scraper();

    for (site in sites) {
        scraper.scrape(site);
    }

    //print("áé");
}




