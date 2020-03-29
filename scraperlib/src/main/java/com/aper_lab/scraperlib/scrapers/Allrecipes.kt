package com.aper_lab.scraperlib.scrapers

import com.aper_lab.scraperlib.ScraperRegistry
import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.api.RecipeScraperAnotation
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep
import com.aper_lab.scraperlib.util.ScrappingHelper
import com.google.gson.GsonBuilder
import org.jsoup.Jsoup
import java.net.URL

@RecipeScraperAnotation()
class Allrecipes : RecipeScraper{

    val gson = GsonBuilder().create();

    override fun scrapFromLink(link: URL):Recipe {
        val doc = Jsoup.connect(link.toString())
                        .get()

        ScrappingHelper.checkWebsiteForJsonLD(doc);

        val recipe = Recipe();
        recipe.link = link.toString();

        val json_ld_text = ScrappingHelper.findJsonLD(doc);

        var recipeFragment = doc.select("[itemtype=\"http://schema.org/Recipe\"]");

        recipe.name = recipeFragment.select("[itemprop=name]").attr("content");
        recipe.time = recipeFragment.select("span.ready-in-time").text()
        recipe.yields = recipeFragment.select("[itemprop=recipeYield]").attr("content")
        recipe.image = recipeFragment.select("img.rec-photo").attr("src")
        recipe.description = recipeFragment.select("[itemprop=description]").attr("content")

        recipe.ingredients = recipeFragment.select("[itemprop=\"recipeIngredient\"]").map {
                element -> Ingredient(element.text(),"");
        }

        recipe.directions = recipeFragment.select("[itemprop=\"recipeInstructions\"]").select(".step .recipe-directions__list--item").mapIndexed {
                id, element -> RecipeStep(id+1,element.text());
        }

        return recipe;
    }

    override fun getSourceID(): String {
        return "allrecipes"
    }

    companion object{

        val  urls = listOf<String>(
            "www.allrecipes.com",
            "allrecipes.com"
        )

        fun Register(registry: ScraperRegistry){
            registry.Register(urls,Allrecipes())
        }
    }
}