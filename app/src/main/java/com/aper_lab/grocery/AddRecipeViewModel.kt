package com.aper_lab.grocery

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper_lab.scraperlib.RecipeAPIService
import kotlinx.coroutines.*

class AddRecipeViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    var successfulImport = MutableLiveData<Boolean>(false);

    init {
        successfulImport.value = false
    }

    fun importRecipe(url: String) {
        successfulImport.postValue(false);
        viewModelScope.launch {
            val rec = RecipeAPIService.getRecipeFromURLAsync(url).await();
            if(rec != null) {
                successfulImport.postValue(true);
            }
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