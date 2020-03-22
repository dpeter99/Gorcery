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

class Recipedatabase : DatabaseConnection {

    val db =Firebase.firestore;

    val recipes = db.collection("recipes")

    override fun storeData(path: String, data: IHasID) {
        db.collection(path).document(data.GetID()).set(data);
    }

    override fun getRecipeByURL(url: String):Recipe? {
        return recipes.whereEqualTo("url", url).get().result?.first()?.toObject<Recipe>();
    }

    override suspend fun getRecipeByID(id: String): Recipe? {
           return recipes.document(id).get().await().toObject<Recipe>();
    }

}