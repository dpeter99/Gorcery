package com.aper_lab.grocery.fragment.addrecipe

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.aper_lab.grocery.User
import com.aper_lab.grocery.util.SingleLiveEvent
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.data.Recipe
import kotlinx.coroutines.*

class AddRecipeViewModel(val user:User) : ViewModel() {

    enum class State{
        Default, //when we open up the fragment
        Loading, //while we are loading the recipe info from the url
        Done,    //When the recipe loading is finished
        Error    //There was an error loading the recipe
    }

    private val _recipe = MutableLiveData<Recipe>();
    val recipe : LiveData<Recipe>
        get() = _recipe;

    private val _state = MutableLiveData<State>();
    val state : LiveData<State>
        get() = _state;

    private val _navigateToRecipe = SingleLiveEvent<String>();
    val navigateToRecipe : SingleLiveEvent<String>
        get() = _navigateToRecipe;

    init {

    }

    fun urlChanged(){
        Log.d("AddRecipe",user.user_id);
        _state.value = State.Default;
    }

    fun getRecipeFromURL(url:String){
        _state.value = State.Loading;
        viewModelScope.launch {
            //Get the recipe at the location
            val rec = RecipeAPIService.getRecipeFromURLAsync(url, false).await();
            if(rec != null){
                _recipe.postValue(rec);
                _state.postValue(State.Done);
            }
        }
    }

    fun saveRecipe(){
        viewModelScope.async {
            if(_recipe.value != null) {
                //RecipeAPIService.saveRecipeToDB(_recipe.value!!);
                GlobalScope.async {
                    User.getInstance().addNewRecipe(_recipe.value!!);
                }.await()
                _navigateToRecipe.value = _recipe.value!!.id;
            }
        }
    }

    @Deprecated("Should use 2 steps to import, (import and save)")
    fun importRecipe(url: String) {
        //successfulImport.postValue(false);
        viewModelScope.launch {
            val rec = RecipeAPIService.getRecipeFromURLAsync(url, true).await();
            if(rec != null) {
                //successfulImport.postValue(true);
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
    }
}