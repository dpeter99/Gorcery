package com.aper_lab.grocery.fragment.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aper_lab.grocery.User

class DiscoverViewModel : ViewModel(){

    private val _profilePicture = MutableLiveData<String>();
    val profilePicture : LiveData<String>
        get() = _profilePicture;


    init {
        _profilePicture.value = User.getInstance().profilePic;
    }
}