package com.aper_lab.scraperlib.data

class Recipe() {
    var name: String = "";
    var link = "";
    var time = "";
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
}