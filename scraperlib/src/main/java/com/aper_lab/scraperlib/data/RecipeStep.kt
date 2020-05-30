package com.aper_lab.scraperlib.data

class RecipeStep (num: Int, text: String) {
    var text:String = text;
    var num = num;

    fun getNumberAsString():String{
        return num.toString();
    }

    constructor() : this(0,"") {

    }
}