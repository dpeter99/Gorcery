package com.aper_lab.grocery.model

import com.aper_lab.scraperlib.data.Ingredient

data class ShoppingItem (
    var name: String?,
    var amount: Float? = 0F
){

    var checked: Boolean = false;

    constructor():this(""){

    }
}