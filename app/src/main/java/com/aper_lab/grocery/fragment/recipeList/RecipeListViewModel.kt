package com.aper_lab.grocery.fragment.recipeList

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.aper_lab.grocery.User
import com.aper_lab.grocery.model.UserRecipe
import com.aper_lab.grocery.util.SingleLiveEvent
import com.aper_lab.scraperlib.data.Recipe


class RecipeListViewModel(user: User): ViewModel() {

    //var recipes = mutableMapOf<String,Recipe>()
    //val recipesLiveData :MutableLiveData<MutableMap<String,Recipe>> = MutableLiveData(mutableMapOf());

    private val _recipes = MutableLiveData<Map<String,Recipe>>();
    val recipes : LiveData<Map<String,UserRecipe>>
        get() = repo;

    var repo: LiveData<Map<String,UserRecipe>> = User.getInstance().recipes;

    var recipesCount : MutableLiveData<String> = MutableLiveData("")

    var _recipeNav = SingleLiveEvent<String>();

    init {
        /*
        RecipeDatabase.getUserRecipesCollection().addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.w(TAG, "Listen failed.", exception)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                //Log.d(TAG, "Current data: ${snapshot.documents}")
                val recipes_mutable = _recipes.value?.toMutableMap() ?: mutableMapOf<String, Recipe>();

                for (item in snapshot.documentChanges){
                    val rec = item.document.toObject<Recipe>();
                    if(item.type == DocumentChange.Type.ADDED || item.type == DocumentChange.Type.MODIFIED) {
                        recipes_mutable.put(rec.id, rec);
                    }
                    else if(item.type == DocumentChange.Type.REMOVED){
                        recipes_mutable.remove(rec.id);
                    }
                }
                viewModelScope.launch {
                    _recipes.postValue(recipes_mutable);
                    recipesCount.postValue(recipes_mutable.size.toString());
                }
            }
            else {
                Log.d(TAG, "Current data: null")
            }
        }
         */

    }

    fun recipeClicked(id: String){
        _recipeNav.value = id;
    }

    class RecipePreview(val name:String){
    }
}