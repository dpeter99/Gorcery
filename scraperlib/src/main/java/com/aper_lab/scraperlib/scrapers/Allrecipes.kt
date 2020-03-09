package com.aper_lab.scraperlib.scrapers

import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.api.RecipeScraperAnotation
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep
import org.jsoup.Jsoup

@RecipeScraperAnotation()
class Allrecipes : RecipeScraper{

    override fun scrapFromLink(link: String):Recipe {
        val doc = Jsoup.connect(link).get()

        val recipe = Recipe();
        recipe.link = link;

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

}