package com.aper_lab.grocery.liveData

import androidx.lifecycle.LiveData
import com.aper_lab.grocery.User
import com.aper_lab.grocery.model.Recipe
import com.aper_lab.grocery.model.UserRecipe
import com.aper_lab.grocery.model.UserRecipeData
import com.aper_lab.grocery.util.DocumentLiveData
import com.aper_lab.grocery.util.livedata
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.UserDataReader
import com.google.firebase.firestore.ktx.toObject

class LiveUserRecipe(documentReference: DocumentReference) : DocumentLiveData<UserRecipe>(documentReference, UserRecipe::class.java)
{
    //var userData: LiveData<UserRecipeData>? = null;

    init {
        //userData = documentReference.collection("/owners").document(User.getInstance().user_id).livedata(UserRecipeData::class.java);
        documentReference.collection("/owners").document(User.getInstance().user_id).addSnapshotListener { value, error ->
            this.value?.let {
                it.userData = value?.toObject<UserRecipeData>()
                this.postValue(this.value);
            }
        }
    }


}