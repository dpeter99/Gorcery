package com.aper_lab.scraperlib.data

import com.aper_lab.scraperlib.api.IHasID
import com.aper_lab.scraperlib.util.jsonAdapters.TimeadApter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import org.threeten.bp.Duration


class Recipe() :IHasID{
    var id: String = "";

    var name: String = "";
    var link = "";

    var time = "";
    var cookTime : Long = 0;
    var prepTime : Long  = 0;
    var totalTime : Long = 0;

    var yields = "";
    var image = "";
    var description = "";
    var ingredients: List<Ingredient> = listOf();
    var directions: List<RecipeStep> = listOf();

    override fun toString(): String {
        var res = "";

        res += this.name + "\n" +
                this.link + "\n" +
                this.time + "\n" +
                this.yields + "\n" +
                this.image + "\n" +
                this.description + "\n";
        if(ingredients.isNotEmpty())
            for (ing in ingredients)
            {
                res += ing.name + " " + ing.amount + "\n";
            }

        if(directions.isNotEmpty())
            for (dir in directions)
            {
                res +=  dir.num.toString() + " " + dir.text + "\n";
            }

        return res;
    }

    override fun GetID(): String{
        return id;
    }

    companion object{
        fun fromSchemaOrg(recipe: com.aper_lab.scraperlib.util.schemaOrg.Recipe):Recipe{
            var rec = Recipe();

            rec.name = recipe.name;
            rec.time = recipe.totalTime;

            if(recipe.cookTime != "")
            rec.cookTime = Duration.parse(recipe.cookTime).seconds;
            if(recipe.prepTime != "")
            rec.prepTime = Duration.parse(recipe.prepTime).seconds;
            if(recipe.totalTime != "")
            rec.totalTime = Duration.parse(recipe.totalTime).seconds;

            rec.yields = recipe.recipeYield.removeSuffix("servings")
            rec.image = recipe.image;
            rec.description = recipe.description;

            rec.ingredients = recipe.recipeIngredient.map {
                    element -> Ingredient(element,"");
            }

            rec.directions = recipe.recipeInstructions.mapIndexed {
                    id, element -> RecipeStep(id+1,(element as LinkedTreeMap<String, String>)["text"].toString());
            }

            return rec;
        }
    }
}