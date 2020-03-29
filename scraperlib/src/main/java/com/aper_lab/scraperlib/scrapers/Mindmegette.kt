package com.aper_lab.scraperlib.scrapers

import com.aper_lab.scraperlib.ScraperRegistry
import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.api.RecipeScraperAnotation
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep
import org.jsoup.Jsoup
import java.net.URL

@RecipeScraperAnotation()
class Mindmegette : RecipeScraper{

    override fun scrapFromLink(link: URL):Recipe {
        val doc = Jsoup.connect(link.toString()).followRedirects(true).get()

        val recipe = Recipe();
        recipe.link = link.toString();

        var recipeFragment = doc.select("[itemtype=\"http://data-vocabulary.org/Recipe\"]");

        recipe.name = recipeFragment.select("h1[itemprop=name]").text();
        recipe.time = recipeFragment.select("[itemprop=totalTime]").text();
        recipe.yields = recipeFragment.select(".portion").text();
        recipe.image = doc.select("meta[property=\"og:image\"]").attr("content")
        recipe.description = "";

        recipe.ingredients = recipeFragment.select("[itemprop=\"ingredient\"]").map {
                element -> Ingredient(element.text(),"");
        }

        recipe.directions = recipeFragment.select("[itemprop=\"instructions\"] ol li").mapIndexed {
                id, element -> RecipeStep(id+1,element.text());
        }

        return recipe;
    }

    override fun getSourceID(): String {
        return "mindmegette";
    }

    companion object{
        val urls = listOf<String>(
            "www.mindmegette.hu",
            "mindmegette.hu"
        )

        fun Register(registry: ScraperRegistry){

            registry.Register(urls,Mindmegette())
        }
    }

}