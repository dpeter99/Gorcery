package com.aper_lab.scraperlib

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main(args: Array<String>) = runBlocking{

    val sites = listOf<String>(
        "https://www.allrecipes.com/recipe/83557/juicy-roasted-chicken/"
        //"https://tasty.co/recipe/one-pot-garlic-parmesan-pasta"
    )

    for (site in sites) {
        launch {
            var a = RecipeAPIService.getRecipeFromURLAsync(site).await();
            print(a.toString())
        }
    }


}


