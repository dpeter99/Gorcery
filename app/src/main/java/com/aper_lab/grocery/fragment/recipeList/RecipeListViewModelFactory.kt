package com.aper_lab.grocery.fragment.recipeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aper_lab.grocery.User

class RecipeListViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {

            return RecipeListViewModel(
                User.getInstance()
            ) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}