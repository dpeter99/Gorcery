package com.aper_lab.grocery.viewModel.recipe

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.aper_lab.grocery.User
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.model.UserRecipe
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.data.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RecipeViewModel(val id : String) : ViewModel() {

    var recipe: MutableLiveData<UserRecipe> = MutableLiveData();

    init {

        GlobalScope.launch {

            var rec = RecipeDatabase.getUserRecipeByID(id)
                ?: UserRecipe(com.aper_lab.grocery.model.Recipe(), null);
            if (rec != null) {
                recipe.postValue(rec);
            }
        }

    }

    fun setRecipeFavorite(fav: Boolean){
        if(recipe.value != null) {
            User.getInstance().setFavoriteRecipe(recipe.value!!, fav);
            val r = recipe.value

            r?.userData?.favorite = fav;
            recipe.postValue(r);
        }
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

