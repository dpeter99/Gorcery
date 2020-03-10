package com.aper_lab.grocery.viewModel.recipe

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.Scraper
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    val recipe: LiveData<Recipe> = liveData {
        val re = Recipe()
        re.name = "test";
        emit(re);
        val rec : Recipe? = RecipeAPIService.GetRecipe("https://www.delish.com/cooking/recipe-ideas/recipes/a55501/best-goulash-recipe/").await();
        emit(rec?: Recipe());
    }

    init {
        //val scraper = Scraper();

        /*
        recipe = liveData {
             val rec = RecipeAPIService.GetRecipe("https://www.delish.com/cooking/recipe-ideas/recipes/a55501/best-goulash-recipe/").await() ?: Recipe();
            emit(rec);
        }
         */

        //recipe.value?.name = "Test Recipe";
/*
        recipe.value?.ingredients = listOf(
            Ingredient("Chicken", "10kg"),
            Ingredient("Chicken", "10kg"),
            Ingredient("Chicken", "10kg"),
            Ingredient("Chicken", "10kg"),
            Ingredient("Chicken", "10kg")
        )

        recipe.value?.directions = listOf(
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

 */

    }
}

@BindingAdapter("recipeName")
fun TextView.setSleepImage(item: Recipe?) {
    item?.let {
        text = item.name;
    }
}