package com.aper_lab.grocery.database

import com.aper_lab.scraperlib.api.DatabaseConnection
import com.aper_lab.scraperlib.api.IHasID
import com.aper_lab.scraperlib.data.Recipe
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.gson.internal.`$Gson$Preconditions`
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await

object RecipeDatabase : DatabaseConnection {

    val db =Firebase.firestore;

    val recipes = db.collection("recipes")

    override fun storeData(path: String, data: IHasID) {
        db.collection(path).document(data.GetID()).set(data);
    }

    override suspend fun getRecipeByURL(url: String):Recipe? {
        val res = recipes.whereEqualTo("url", url).get().await();
        if(res.size() > 0){
            return res.first()?.toObject<Recipe>();
        }
        else{
            return null;
        }

    }

    override suspend fun getRecipeByID(id: String): Recipe? {
        return recipes.document(id).get().await().toObject<Recipe>();
    }

    override fun updateRecipe(path: String, recipe: Recipe) {
        recipes.document(recipe.GetID()).set(recipe);
    }


}