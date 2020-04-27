package com.aper_lab.grocery.viewModel.recipe

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.data.Recipe

class RecipeViewModel(val id : String) : ViewModel() {

    var recipe: LiveData<Recipe> = liveData {
        var rec = Recipe.create();
        emit(rec);
        rec = RecipeAPIService.getRecipeByIDAsync(id).await()?: Recipe.create();
        emit(rec);
    }

}


class RecipeViewModelFactory(private val id: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

