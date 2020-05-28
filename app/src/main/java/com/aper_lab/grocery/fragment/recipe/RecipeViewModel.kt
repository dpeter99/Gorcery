package com.aper_lab.grocery.fragment.recipe

import androidx.lifecycle.*
import com.aper_lab.grocery.User
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.model.UserRecipe
import com.aper_lab.scraperlib.RecipeAPIService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecipeViewModel(val id : String) : ViewModel() {

    var recipe: MutableLiveData<UserRecipe> = MutableLiveData();

    var recipeSource: MutableLiveData<String> = MutableLiveData();

    init {

        GlobalScope.launch {

            var rec = RecipeDatabase.getUserRecipeByID(id)
                ?: UserRecipe(com.aper_lab.grocery.model.Recipe(), null);
            if (rec != null) {
                recipe.postValue(rec);
                recipeSource.postValue(RecipeAPIService.getSourceNameFromURL(rec.recipe.link))
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

