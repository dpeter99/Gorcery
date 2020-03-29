package com.aper_lab.scraperlib.scrapers

import com.aper_lab.scraperlib.ScraperRegistry
import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.api.RecipeScraperAnotation
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep
import org.jsoup.Jsoup
import java.net.URL
import java.nio.charset.Charset

@RecipeScraperAnotation()
class Nosalty : RecipeScraper{

    override fun scrapFromLink(link: URL):Recipe {
        val connection = Jsoup.connect(link.toString())
                                .followRedirects(true)

        val doc = connection.get()
        doc.charset(Charset.forName("UTF-8"))

        val recipe = Recipe();
        recipe.link = link.toString();

        var recipeFragment = doc.select("[itemtype=\"https://data-vocabulary.org/Recipe\"]");

        recipe.name = recipeFragment.select("h1[itemprop=name]")[0].text();
        recipe.time = recipeFragment.select(".clearfix.nosalty-recept-bottom-section div.right-text.dont-print span").text()
        recipe.yields = recipeFragment.select("[itemprop=yield]")[0].text()
        recipe.image = doc.select("link[rel=image_src]").attr("href")
        recipe.description = recipeFragment.select("[itemprop=summary]").select("p").text()

        recipe.ingredients = recipeFragment.select("[itemprop=\"ingredient\"]").map {
                element -> Ingredient(element.text(),"");
        }

        recipe.directions = recipeFragment.select(".recept-elkeszites .column-block-content ol").select("li").mapIndexed {
                id, element -> RecipeStep(id+1,element.text());
        }

        return recipe;
    }

    override fun getSourceID(): String {
        return "nosalty"
    }


    companion object{
        val urls = listOf<String>(
            "www.nosalty.hu",
            "nosalty.hu",
            "m.nosalty.hu"
        )

        fun Register(registry: ScraperRegistry){

            registry.Register(urls,Nosalty())
        }
    }

}