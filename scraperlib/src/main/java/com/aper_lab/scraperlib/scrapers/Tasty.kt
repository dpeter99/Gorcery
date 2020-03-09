package com.aper_lab.scraperlib.scrapers

import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.api.RecipeScraperAnotation
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep
import org.jsoup.Jsoup
import java.nio.charset.Charset

@RecipeScraperAnotation()
class Tasty : RecipeScraper{

    override fun scrapFromLink(link: String):Recipe {
        val doc = Jsoup.connect(link).get()
        doc.charset(Charset.forName("UTF-8"))

        val recipe = Recipe();
        recipe.link = link;

        var recipeFragment = doc.select(".recipe-page");

        recipe.name = recipeFragment.select("h1.recipe-name").text();
        //recipe.time = recipeFragment.select(".total-time-amount").text()
        recipe.yields = recipeFragment.select(".servings-display").text()
        recipe.image = doc.select("meta[property=og:image]").attr("content")
        //recipe.description = "";


        recipe.ingredients = recipeFragment.select(".ingredients__section .ingredient").map {
                element -> Ingredient(element.text(),"");
        }


        recipe.directions = recipeFragment.select(".preparation .prep-steps li").mapIndexed {
                id, element -> RecipeStep(id+1,element.text());
        }


        return recipe;
    }

}