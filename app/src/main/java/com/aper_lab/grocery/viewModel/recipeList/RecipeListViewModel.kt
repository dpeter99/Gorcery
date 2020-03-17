package com.aper_lab.grocery.viewModel.recipeList

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RecipeListViewModel: ViewModel() {

    val db = Firebase.firestore

    val recipes = mutableListOf<RecipePreview>()

    init {
        db.collection("recipes").get()
            .addOnSuccessListener {result ->
                for (recipe in result) {
                    recipes.add(RecipePreview(recipe.name))
                }
            }
    }


    class RecipePreview(val name:String){
    }
}