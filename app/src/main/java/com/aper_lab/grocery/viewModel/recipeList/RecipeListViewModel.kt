package com.aper_lab.grocery.viewModel.recipeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aper_lab.grocery.util.SingleLiveEvent
import com.aper_lab.scraperlib.data.Recipe
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RecipeListViewModel: ViewModel() {

    val db = Firebase.firestore

    var recipes = mutableListOf<Recipe>()
    val recipesLiveData :MutableLiveData<List<Recipe>> = MutableLiveData();

    var recipesCount : MutableLiveData<String> = MutableLiveData("")

    var _recipeNav = SingleLiveEvent<Recipe>();

    init {
        db.collection("recipes").get()
            .addOnSuccessListener {result ->
                val recipes_new = mutableListOf<Recipe>()
                for (recipe in result) {
                    recipes_new.add(recipe.toObject<Recipe>())
                    //recipes_new.add(RecipePreview(recipe["name"].toString()))
                }
                recipes = recipes_new;
                recipesLiveData.value = recipes;

                recipesCount.value = recipes.count().toString();

            }
    }

    fun recipeClicked(){
        _recipeNav.value = recipes[0];
    }

    class RecipePreview(val name:String){
    }
}