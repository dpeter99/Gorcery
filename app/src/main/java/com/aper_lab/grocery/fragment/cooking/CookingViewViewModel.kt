package com.aper_lab.grocery.fragment.cooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.fragment.recipe.RecipeViewModel
import com.aper_lab.grocery.model.UserRecipe
import com.aper_lab.scraperlib.RecipeAPIService

class CookingViewViewModel(id: String) : ViewModel() {
    // TODO: Implement the ViewModel

    val recieID: String = id;

    val recipe: LiveData<UserRecipe> = liveData {
        var rec = RecipeDatabase.getUserRecipeByID(id);
        if (rec != null) {
            emit(rec!!);
        }
    }


    class ViewModelFactory(private val id: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CookingViewViewModel::class.java)) {
                return CookingViewViewModel(id) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}