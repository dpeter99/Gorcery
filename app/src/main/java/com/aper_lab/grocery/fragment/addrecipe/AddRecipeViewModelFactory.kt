package com.aper_lab.grocery.fragment.addrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aper_lab.grocery.User

class AddRecipeViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddRecipeViewModel::class.java)) {

            return AddRecipeViewModel(User.getInstance()) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}