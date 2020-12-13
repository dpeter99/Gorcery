package com.aper_lab.grocery.fragment.recipe.recipeTagMenu

import androidx.lifecycle.*
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.UserData

class RecipeTagMenuViewModel(var recipeID: String) : ViewModel() {

    //var tags = MutableLiveData<List<Pair<String, Boolean>>>(listOf());

    //var user = RecipeDatabase.getLoggedInUser();

    val tags = Transformations.map(UserData.instance.user) { it ->
        it?.tags?.map { i -> Pair(i.name, i.hasRecipe(recipeID)) }
    }


    fun setTag(name: String, state:Boolean){
        var tag = UserData.instance.user.value?.tags?.find { it.name == name }
        if(state){
            tag?.recipes?.add(recipeID);
        }
        else{
            tag?.recipes?.remove(recipeID);
        }

        RecipeDatabase.updateUser(UserData.instance.user);
    }


    companion object {
        fun provideFactory(recipeID: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    //return assistedFactory.create(recipeID) as T
                    return RecipeTagMenuViewModel(recipeID) as T
                }
            }
    }
}
