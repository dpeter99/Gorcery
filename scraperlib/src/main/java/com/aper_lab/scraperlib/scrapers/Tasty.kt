package com.aper_lab.scraperlib.scrapers

import com.aper_lab.scraperlib.ScraperRegistry
import com.aper_lab.scraperlib.api.RecipeScraper
import com.aper_lab.scraperlib.api.RecipeScraperAnotation
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep
import com.aper_lab.scraperlib.util.ScrappingHelper
import com.google.gson.GsonBuilder
import com.google.gson.internal.LinkedTreeMap
import org.jsoup.Jsoup
import org.threeten.bp.Duration
import java.net.URL
import java.nio.charset.Charset


@RecipeScraperAnotation()
class Tasty : RecipeScraper{

    val gson = GsonBuilder().create();

    override fun scrapFromLink(link: URL):Recipe {
        val doc = ScrappingHelper.getDocFromURL(link);

        var recipeFragment = doc.select(".recipe-page");

        val json_ld_text = ScrappingHelper.findJsonLD(doc);

        var recipe = Recipe()

        ScrappingHelper.checkWebsiteForJsonLD(doc);

        if(json_ld_text != null) {
            val jsonObject = ScrappingHelper.recipeFromJsonLD(json_ld_text);
            recipe = Recipe.fromSchemaOrg(jsonObject?: com.aper_lab.scraperlib.util.schemaOrg.Recipe());

            recipe.yields = jsonObject?.recipeYield?.removeSuffix("servings")?: ""
        }


        recipe.link = link.toString();

        recipe.name = recipeFragment.select("h1.recipe-name").text();



        return recipe;
    }

    override fun getSourceID(): String {
        return "tasty"
    }

    companion object{
        val urls = listOf<String>(
            "tasty.co"
        )

        fun Register(registry: ScraperRegistry){

            registry.Register(urls,Tasty())
        }
    }

}