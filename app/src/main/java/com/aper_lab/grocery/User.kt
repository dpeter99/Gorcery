package com.aper_lab.grocery

import android.util.Log
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.model.toDomainModel
import com.aper_lab.scraperlib.data.Recipe
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.coroutineScope

class User(id: String) {

    val user_id: String = id;

    suspend fun addNewRecipe(recipe: Recipe){
        //Check if the recipe already exists in the database, if so only add the user to the owners
        val rec = RecipeDatabase.getRecipeByID(recipe.id);
        if(rec != null){
            if(rec.owners_id.contains(user_id)){
            }
            else {
                rec.owners_id.add(user_id);
                RecipeDatabase.updateRecipe(rec);
            }
        }
        else{
            //If the recipe does not exist yet
            val new_rec = recipe.toDomainModel(this.user_id);
            RecipeDatabase.updateRecipe(new_rec);
        }
        Log.e("AddRecipe","Should be done by now");
    }

    /*
        suspend fun addNewRecipe(recipe: Recipe){
        //Check if the recipe already exists in the database, if so only add the user to the owners
        Log.e("AddRecipe","Trying to add recipe to user. Recipe id:" + recipe.id +
                "Recipe name: " + recipe.name);
        Thread.dumpStack();

        val rec = RecipeDatabase.getRecipeByID(recipe.id);
        assert(rec == null);
        if(rec != null){
            Log.e("AddRecipe","Found recipe in db. Recipe id:" + rec.id +
                    "Recipe name: " + rec.name +
                    "Recipe owners: " + rec.owners_id.joinToString { it });

            if(rec.owners_id.contains(user_id)){
                Log.e("AddRecipe","User already has this recipe");
            }
            else {
                Log.e("AddRecipe","Added recipe to user");
                rec.owners_id.add(user_id);
                RecipeDatabase.updateRecipe(rec);
            }
        }
        else{//If the recipe does not exist yet
            Log.e("AddRecipe","Adding new recipe to the database")
            val new_rec = recipe.toDomainModel(this.user_id);
            RecipeDatabase.updateRecipe(new_rec);
        }
        Log.e("AddRecipe","Should be done by now");
    }
     */

    suspend fun updateRecipe(recipe: Recipe){
        //Check if the recipe already exists, if so only add the user to the owners
        val rec = RecipeDatabase.getRecipeByID(recipe.id);
        if(rec != null){
            if(!rec.owners_id.contains(user_id)){
                Log.e("AddRecipe","User doesn't have this recipe");
            }
            else {
                RecipeDatabase.updateRecipe(rec);
            }
        }
        //If the recipe does not exist yet
        else{
            val new_rec = recipe.toDomainModel(this.user_id);
            RecipeDatabase.storeRecipe(new_rec);
        }
    }

    companion object {
        private var instance: User? = null;


        public fun getInstance(): User {
            if (instance == null) {
                Log.e("User", "There is no user yet :(");
            }
            return instance!!;
        }

        fun signIn(user: FirebaseUser) {
            Log.i("User", "User logged in");
            instance = User(user.uid);
        }
    }

}