package com.aper_lab.grocery.fragment.recipeList

import androidx.lifecycle.*
import com.aper_lab.grocery.liveData.LiveUserRecipeList
import com.aper_lab.grocery.util.SingleLiveEvent
import com.aper_lab.grocery.UserData
import com.aper_lab.grocery.database.RecipeDatabase


class RecipeListViewModel(var tag:String? = ""): ViewModel() {

    //var recipes = mutableMapOf<String,Recipe>()
    //val recipesLiveData :MutableLiveData<MutableMap<String,Recipe>> = MutableLiveData(mutableMapOf());

    //private val _recipes = MutableLiveData<Map<String,Recipe>>();
    val recipes : LiveUserRecipeList?
        get() = repo;

    var repo: LiveUserRecipeList? =null// = UserData.instance.recipes;

    var recipesCount : MutableLiveData<String> = MutableLiveData("")

    var _recipeNav = SingleLiveEvent<String>();

    init {
        if(tag.isNullOrBlank()){
            repo = UserData.instance.recipes;
        }
        else{
            val t = UserData.instance.user.value?.tags?.find { it.name == tag }
            t?.let {
                if(it.recipes.isNotEmpty()) {
                    repo = RecipeDatabase.getRecipesInTag(it)
                }
            };
        }
    }

    fun recipeClicked(id: String){
        _recipeNav.value = id;
    }


    companion object {
        fun provideFactory(tag: String?): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    //return assistedFactory.create(recipeID) as T
                    return RecipeListViewModel(tag) as T
                }
            }
    }
}