package com.aper_lab.grocery

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.data.Recipe
import kotlinx.coroutines.*

class AddRecipeViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private var _succesfullImport = MutableLiveData<Boolean>();
    val succesfullImport: LiveData<Boolean>
        get() = _succesfullImport;

    init {
        _succesfullImport.value = false
    }

    fun importRecipe(url: String) {
        viewModelScope.launch {
            //val rec = RecipeAPIService.getRecipeFromURLAsync(url).await();
            _succesfullImport.postValue( true);
        }

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
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