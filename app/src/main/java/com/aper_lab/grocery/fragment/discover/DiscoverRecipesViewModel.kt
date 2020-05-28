package com.aper_lab.grocery.fragment.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aper_lab.grocery.User
import com.aper_lab.grocery.model.Recipe
import com.aper_lab.grocery.model.UserRecipe
import com.aper_lab.grocery.util.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DiscoverRecipesViewModel : ViewModel(){

    private val _profilePicture = MutableLiveData<String>();
    val profilePicture : LiveData<String>
        get() = _profilePicture;

    private val _mainRecipe = MutableLiveData<UserRecipe>();
    val mainRecipe : LiveData<UserRecipe>
        get() = _mainRecipe;

    private val _secondaryRecipes = MutableLiveData<List<UserRecipe>>();
    val secondaryRecipes : LiveData<List<UserRecipe>>
        get() = _secondaryRecipes;

    var _recipeNav = SingleLiveEvent<String>();

    init {
        _profilePicture.value = User.getInstance().profilePic;

        refresRecipes();
    }

    fun refresRecipes(){
        GlobalScope.launch {
            refresRecipesAsync();
        }
    }

    suspend fun refresRecipesAsync(){
            val rec = User.getInstance().getDiscoverRecipes();

            _mainRecipe.postValue(rec[0]);

            _secondaryRecipes.postValue(rec.subList(1,rec.size-1));
    }

    fun openMainRecipe(){
        _recipeNav.postValue(_mainRecipe.value?.recipe?.id?: "")
    }

    fun openSecondaryRecipe(i:Int){
        val rec = _secondaryRecipes.value?.get(i)?.recipe?.id;
        _recipeNav.postValue(rec);
    }
}