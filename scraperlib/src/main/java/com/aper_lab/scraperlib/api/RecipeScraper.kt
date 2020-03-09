package com.aper_lab.scraperlib.api

import com.aper_lab.scraperlib.data.Recipe

interface RecipeScraper {

    fun scrapFromLink(link: String):Recipe;


    fun processIngredient(){

    }
}