package com.aper_lab.grocery.model

open class Recipe: com.aper_lab.scraperlib.data.Recipe() {

    var owners_id: MutableList<String> = mutableListOf();

    fun addOwner(userID: String){
        if(!owners_id.contains(userID))
        owners_id.add(userID)
    }

    fun removeOwner(userID: String){
        owners_id.remove(userID);
    }

    fun hasOwner(userID: String):Boolean{
        return owners_id.contains(userID);
    }

}

data class UserRecipe(var recipe: Recipe, var userData: UserRecipeData?) {

}


fun com.aper_lab.scraperlib.data.Recipe.toDomainModel(): Recipe {
    var recipe = Recipe();
    this.copy(recipe);
    return recipe;
}

fun com.aper_lab.scraperlib.data.Recipe.toDomainModel(userID: String): Recipe {
    var recipe = this.toDomainModel();
    recipe.addOwner(userID);
    return recipe;
}