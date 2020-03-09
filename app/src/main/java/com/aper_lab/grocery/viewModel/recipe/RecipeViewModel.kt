package com.aper_lab.grocery.viewModel.recipe

import androidx.lifecycle.ViewModel
import com.aper_lab.scraperlib.Scraper
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep

class RecipeViewModel : ViewModel() {

    var recipe: Recipe

    init {
        //val scraper = Scraper();

        //recipe = scraper.scrape("https://www.delish.com/cooking/recipe-ideas/recipes/a55501/best-goulash-recipe/") ?: Recipe();
        recipe = Recipe();
        recipe.name = "Test Recipe";

        recipe.ingredients = listOf(
            Ingredient("Chicken", "10kg"),
            Ingredient("Chicken", "10kg"),
            Ingredient("Chicken", "10kg"),
            Ingredient("Chicken", "10kg"),
            Ingredient("Chicken", "10kg")
        )

        recipe.directions = listOf(
            RecipeStep(
                1,
                "Preheat oven to 425 degrees F (220 degrees C). Grease a 9x13-inch baking dish"
            ),
            RecipeStep(
                2,
                "Preheat oven to 425 degrees F (220 degrees C). Grease a 9x13-inch baking dish"
            ),
            RecipeStep(
                3,
                "Preheat oven to 425 degrees F (220 degrees C). Grease a 9x13-inch baking dish"
            ),
            RecipeStep(
                4,
                "Preheat oven to 425 degrees F (220 degrees C). Grease a 9x13-inch baking dish"
            )
        )

    }
}