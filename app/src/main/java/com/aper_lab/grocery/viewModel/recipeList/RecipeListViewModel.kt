package com.aper_lab.grocery.viewModel.recipeList

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.util.SingleLiveEvent
import com.aper_lab.scraperlib.data.Recipe
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class RecipeListViewModel: ViewModel() {

    var recipes = mutableMapOf<String,Recipe>()
    val recipesLiveData :MutableLiveData<MutableMap<String,Recipe>> = MutableLiveData(mutableMapOf());

    var recipesCount : MutableLiveData<String> = MutableLiveData("")

    var _recipeNav = SingleLiveEvent<String>();

    init {

        RecipeDatabase.recipes.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.w(TAG, "Listen failed.", exception)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                Log.d(TAG, "Current data: ${snapshot.documents}")

                for (item in snapshot.documentChanges){
                    val rec = item.document.toObject<Recipe>();
                    if(item.type == DocumentChange.Type.ADDED || item.type == DocumentChange.Type.MODIFIED) {
                        recipes.put(rec.id, rec);
                    }
                    else if(item.type == DocumentChange.Type.REMOVED){
                        recipes.remove(rec.id);
                    }
                }
                recipesLiveData.postValue(recipes);


                recipesCount.postValue(recipesLiveData.value?.size.toString());

            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }

    fun recipeClicked(id: String){
        _recipeNav.value = id;
    }

    fun refreshRecipeList(){

    }

    class RecipePreview(val name:String){
    }
}