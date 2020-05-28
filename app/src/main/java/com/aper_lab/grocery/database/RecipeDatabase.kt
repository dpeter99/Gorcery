package com.aper_lab.grocery.database

import android.util.Log
import com.aper_lab.grocery.User
import com.aper_lab.grocery.model.Recipe
import com.aper_lab.grocery.model.UserRecipe
import com.aper_lab.grocery.model.UserRecipeData
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
import kotlin.random.Random

object RecipeDatabase {

    val db = Firebase.firestore;

    val recipes = db.collection("recipes")

    private var user_id: String? = null;

    init {
        FirebaseAuth.getInstance().addAuthStateListener {
            val user = it.currentUser
            if (user != null) {
                user_id = user.uid;
            }
        }
    }


    /**
     * Stores the given recipe in the database
     * If already exists it overrides it.
     */
    fun storeRecipe(recipe: Recipe) {
        recipes.document(recipe.GetID()).set(recipe);
    }

    @Deprecated("Should use getUserRecipeByURL")
    suspend fun getRecipeByURL(url: String): Recipe? {
        val res = recipes.whereEqualTo("url", url).get().await();
        if (res.size() > 0) {
            return res.first()?.toObject<com.aper_lab.grocery.model.Recipe>();
        } else {
            return null;
        }
    }

    suspend fun getUserRecipeByURL(url: String): UserRecipe? {
        val res = recipes.whereEqualTo("url", url).get().await();
        if (res.size() > 0) {
            val r = res.first()?.toObject<com.aper_lab.grocery.model.Recipe>();
            val u =
                recipes.document(r!!.id).collection("/owners").document(User.getInstance().user_id)
                    .get().await().toObject<UserRecipeData>();
            return UserRecipe(r, u);
        } else {
            return null;
        }
    }


    suspend fun getRecipeByID(id: String): Recipe? {
        return recipes.document(id).get().await().toObject<Recipe>();
    }

    suspend fun getUserRecipeByID(id: String): UserRecipe? {
        val r = recipes.document(id).get().await().toObject<Recipe>();
        if(r != null) {
            val u =
                recipes.document(id).collection("/owners").document(User.getInstance().user_id).get()
                    .await().toObject<UserRecipeData>();
            return UserRecipe(r, u);
        }
        return null;
    }


    suspend fun getUserDiscoverrecipes():List<UserRecipe>{
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789"
        val rand = java.util.Random().ints(15, 0, source.length-1)
            .toArray()
            .map(source::get)
            .joinToString("")
        val rand_id = com.aper_lab.scraperlib.util.HashUtils.md5(rand);


        val rec = recipes.whereGreaterThanOrEqualTo("id", rand_id)
                .orderBy("id")
                .limit(3)
                .get()
                .await();

        val rec_list = rec.toObjects<Recipe>().toMutableList()

        if(rec.count() < 3){
            val recipes_next = recipes.whereGreaterThanOrEqualTo("id", "A")
                    .orderBy("id")
                    .limit((3 - rec.count()).toLong())
                    .get()
                    .await();
             rec_list.addAll(recipes_next.toObjects<Recipe>());
        }

        val discover_recipes = rec_list.map {
            val userData = getUserRecipeData(it)
            UserRecipe(it,userData)
        }

        return discover_recipes;
    }


    suspend fun getUserRecipes(): List<Recipe>? {
        checkUserID()

        val res = db.collection("recipes").whereArrayContains("owners_id", user_id ?: "")
            .get().await();
        if (res.size() > 0) {
            return res.toObjects<Recipe>();
        } else {
            return null;
        }
    }

    fun getUserRecipesCollection(): Query {
        val id = User.getInstance().user_id
        return db.collection("recipes").whereArrayContains("owners_id", id)
    }

    suspend fun getUserRecipeData(rec: Recipe): UserRecipeData? {
        val u = recipes.document(rec.id).collection("/owners").document(User.getInstance().user_id).get().await();
        return u.toObject<UserRecipeData>();
    }

    fun storeUserRecipeData(rec: Recipe, data: UserRecipeData) {
        recipes.document(rec.GetID()).collection("/owners").document(data.userID).set(data);
    }


    private fun checkUserID() {
        if (user_id.isNullOrBlank()) {
            Log.e("Database", "You don't have a user id but want todo database operations");
        }
    }


}