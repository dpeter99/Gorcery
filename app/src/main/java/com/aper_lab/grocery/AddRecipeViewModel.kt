package com.aper_lab.grocery

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.data.Recipe
import kotlinx.coroutines.*

class AddRecipeViewModel : ViewModel() {

    enum class State{
        Default,
        Loading,
        Done,
        Error
    }

    private val _recipe = MutableLiveData<Recipe>();
    val recipe : LiveData<Recipe>
        get() = _recipe;

    private val _state = MutableLiveData<State>();
    val state : LiveData<State>
        get() = _state;

    init {

    }

    fun urlChanged(){
        _state.value = State.Default;
    }

    fun getRecipeFromURL(url:String){
        _state.postValue(State.Loading);
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
        viewModelScope.launch {
            if(_recipe.value != null) {
                RecipeAPIService.saveRecipeToDB(_recipe.value!!);
            }
        }
    }

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

@BindingAdapter("visible")
fun ImageView.setVisibility(item: Boolean?) {
    item?.let {
        visibility = if(it){
            View.VISIBLE;
        } else {
            View.INVISIBLE;
        }
    }
}