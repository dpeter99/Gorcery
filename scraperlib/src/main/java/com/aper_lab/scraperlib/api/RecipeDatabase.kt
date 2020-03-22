package com.aper_lab.scraperlib.api

import com.aper_lab.scraperlib.data.Recipe

interface DatabaseConnection {

    fun storeData(path: String, data: IHasID);

    //#################################
    //RECIPE
    //#################################
    fun getRecipeByURL(url: String):Recipe?;

    suspend fun getRecipeByID(id: String):Recipe?;
}