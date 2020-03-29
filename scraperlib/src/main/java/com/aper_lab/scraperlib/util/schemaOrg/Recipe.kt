package com.aper_lab.scraperlib.util.schemaOrg

class Recipe : JsonLDData() {
    var name:String = "";
    var description:String = "";

    var image: String = "";

    var keywords: String = "";
    var recipeCategory: String = "";
    var recipeCuisine: String = "";

    var recipeIngredient:List<String> = listOf()
    var recipeInstructions:List<Any> = listOf()

    var recipeYield: String = "";

    var cookTime: String = "";
    var prepTime: String = "";
    var totalTime: String = "";

    var nutrition: Any? = null;

    var tool: Any? = null;

    var datePublished: String = "";

    override fun getType():String{
        return "Recipe"
    }

}