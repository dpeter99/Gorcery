package com.aper_lab.grocery.model

class UserRecipeData(user:String) {

    var userID: String = user;

    var favorite:Boolean = false;

    constructor() : this("") {
    }

}