package com.aper_lab.grocery.model

open class Recipe: com.aper_lab.scraperlib.data.Recipe() {

    var owners_id: MutableList<String> = mutableListOf();

}

class UserRecipe(var recipe: Recipe, var userData: UserRecipeData?) {

}


fun com.aper_lab.scraperlib.data.Recipe.toDomainModel(): Recipe {
    var recipe = Recipe();
    this.copy(recipe);
    return recipe;
}

fun com.aper_lab.scraperlib.data.Recipe.toDomainModel(userID: String): Recipe {
    var recipe = this.toDomainModel();
    recipe.owners_id.add(userID);
    return recipe;
}