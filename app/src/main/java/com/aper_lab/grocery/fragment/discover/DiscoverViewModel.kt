package com.aper_lab.grocery.fragment.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aper_lab.grocery.User
import com.aper_lab.grocery.model.Recipe
import com.aper_lab.grocery.model.UserRecipe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DiscoverViewModel : ViewModel(){

    private val _profilePicture = MutableLiveData<String>();
    val profilePicture : LiveData<String>
        get() = _profilePicture;

    private val _mainRecipe = MutableLiveData<UserRecipe>();
    val mainRecipe : LiveData<UserRecipe>
        get() = _mainRecipe;

    private val _secondaryRecipes = MutableLiveData<List<UserRecipe>>();
    val secondaryRecipes : LiveData<List<UserRecipe>>
        get() = _secondaryRecipes;


    init {
        _profilePicture.value = User.getInstance().profilePic;

        GlobalScope.launch {
            val rec = User.getInstance().getDiscoverRecipes();

            _mainRecipe.postValue(rec[0]);

            _secondaryRecipes.postValue(rec.subList(1,rec.size-1));
        }
    }
}