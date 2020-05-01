package com.aper_lab.grocery.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aper_lab.scraperlib.data.Recipe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class UserProfileViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val user = FirebaseAuth.getInstance().currentUser;

    private val _profilePicture = MutableLiveData<String>();
    val profilePicture : LiveData<String>
        get() = _profilePicture;

    private val _profileName = MutableLiveData<String>();
    val profileName : LiveData<String>
        get() = _profileName;

    private val _profileEmail = MutableLiveData<String>();
    val profileEmail : LiveData<String>
        get() = _profileEmail;


    init{
         _profilePicture.value = user?.photoUrl.toString();
        _profileName.value = user?.displayName;
        _profileEmail.value = user?.email;
    }

    fun logOut(){
        FirebaseAuth.getInstance().signOut();
    }

}
