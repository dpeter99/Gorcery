package com.aper_lab.grocery.database

import android.util.Log
import com.aper_lab.grocery.User
import com.aper_lab.grocery.model.Recipe
import com.aper_lab.grocery.model.toDomainModel
import com.aper_lab.scraperlib.api.DatabaseConnection
import com.aper_lab.scraperlib.api.IHasID
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.gson.internal.`$Gson$Preconditions`
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await

object RecipeDatabase {

    val db =Firebase.firestore;

    val recipes = db.collection("recipes")

    private var user_id:String? = null;

    init{
        FirebaseAuth.getInstance().addAuthStateListener {
            val user = it.currentUser
            if(user!=null){
                user_id = user.uid;
            }
        }
    }

    fun storeRecipe(recipe: com.aper_lab.grocery.model.Recipe){
        recipes.document(recipe.GetID()).set(recipe);
    }

    suspend fun getRecipeByURL(url: String):Recipe? {
        val res = recipes.whereEqualTo("url", url).get().await();
        if(res.size() > 0){
            return res.first()?.toObject<com.aper_lab.grocery.model.Recipe>();
        }
        else{
            return null;
        }
    }

    suspend fun getRecipeByID(id: String): Recipe? {
        return recipes.document(id).get().await().toObject<Recipe>();
    }

    fun updateRecipe(recipe: Recipe) {
        recipes.document(recipe.GetID()).set(recipe);
    }


    suspend fun getUserRecipes(): List<Recipe>?{
        checkUserID()

        val res = db.collection("recipes").whereArrayContains("owners_id", user_id?: "")
            .get().await();
        if(res.size() > 0){
            return res.toObjects<Recipe>();
        }
        else{
            return null;
        }
    }

    fun getUserRecipesCollection(): Query {
        val id = User.getInstance().user_id
        return db.collection("recipes").whereArrayContains("owners_id", id)
    }


    private fun checkUserID() {
        if (user_id.isNullOrBlank()) {
            Log.e("Database", "You don't have a user id but want todo database operations");
        }
    }
}