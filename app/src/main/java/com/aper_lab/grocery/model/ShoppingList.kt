package com.aper_lab.grocery.model

import com.aper_lab.grocery.User
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

data class ShoppingList(

    @DocumentId
    var id:String? = "",
    var owner: String = "",

    var items: MutableList<ShoppingItem> = mutableListOf()
) {

    val hasCheckedItems: Boolean
        get() {
            return items.any { it -> it.checked };
        }

    constructor() : this("","") {

    }

    constructor(owner: String) : this(null,owner) {

    }
}

