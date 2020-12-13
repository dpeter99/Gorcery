package com.aper_lab.grocery.fragment.tags

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.model.RecipeTag
import com.aper_lab.grocery.UserData

class UserTagsViewModel: ViewModel() {

    var tags = Transformations.map(UserData.instance.user) {
        it?.let {
            it.tags
        }
    }

    fun addNewTag(name: String){
        if(tags.value?.any { it.name == name } == false) {
            UserData.instance.user.value?.let {
                it.tags.add(RecipeTag(name));
            }
            RecipeDatabase.updateUser(UserData.instance.user);
        }
    }

}