package com.aper_lab.grocery.model

import com.google.firebase.firestore.DocumentId

data class User(

    @DocumentId
    val id:String? = null,

    var name: String = "",
    var profilePic: String = "",

    var tags: MutableList<RecipeTag> = mutableListOf()
){



}
