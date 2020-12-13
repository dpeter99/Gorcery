package com.aper_lab.grocery.model

import com.google.firebase.firestore.DocumentId

data class RecipeTag(

    var name: String,
    var recipes: MutableList<String> = mutableListOf()
) {

    constructor():this("");

    fun hasRecipe(id: String): Boolean{
        return recipes.any { it -> it == id };
    }

}