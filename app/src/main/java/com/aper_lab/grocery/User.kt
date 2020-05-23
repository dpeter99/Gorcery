package com.aper_lab.grocery

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.model.UserRecipe
import com.aper_lab.grocery.model.UserRecipeData
import com.aper_lab.grocery.model.Recipe as Recipe
import com.aper_lab.scraperlib.data.Recipe as ScrapperRecipe
import com.aper_lab.grocery.model.toDomainModel
import com.aper_lab.scraperlib.RecipeAPIService.scope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class User(id: String) {

    val user_id: String = id;

    private var _recipes: MutableLiveData<Map<String, UserRecipe>> =
        MutableLiveData<Map<String, UserRecipe>>();
    val recipes: LiveData<Map<String, UserRecipe>>
        get() = _recipes;

    init {
        User.instance = this;

        RecipeDatabase.getUserRecipesCollection().addSnapshotListener { snapshot, exception ->
            scope.launch {

                if (exception != null) {
                    Log.w(ContentValues.TAG, "Listen failed.", exception)
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val recipes_mutable = _recipes.value?.toMutableMap()
                        ?: mutableMapOf<String, UserRecipe>();

                    for (item in snapshot.documentChanges) {

                        val rec = item.document.toObject<Recipe>();
                        if (item.type == DocumentChange.Type.ADDED || item.type == DocumentChange.Type.MODIFIED) {
                            var u = RecipeDatabase.getUserRecipeData(rec);
                            recipes_mutable.put(rec.id, UserRecipe(rec, u));
                        } else if (item.type == DocumentChange.Type.REMOVED) {
                            recipes_mutable.remove(rec.id);
                        }
                    }

                    _recipes.postValue(recipes_mutable);
                } else {
                    Log.d(ContentValues.TAG, "Current data: null")
                }
            }
        }

        RecipeDatabase.db.collectionGroup("owners").whereEqualTo("userID",user_id).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.w(ContentValues.TAG, "Listen failed.", exception)
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val recipes_mutable = _recipes.value?.toMutableMap()
                    ?: mutableMapOf<String, UserRecipe>();

                for (item in snapshot.documentChanges) {

                    val user_data = item.document.toObject<UserRecipeData>();
                    if (item.type == DocumentChange.Type.ADDED || item.type == DocumentChange.Type.MODIFIED) {
                        val a = item.document.reference.path.split("/")[1];
                        val r = recipes_mutable.get(a)
                        if(r != null) {
                            r.userData = user_data;
                            recipes_mutable.put(r.recipe.id,r);
                        }

                    } else if (item.type == DocumentChange.Type.REMOVED) {
                        //recipes_mutable.remove(rec.id);
                    }
                }

                _recipes.postValue(recipes_mutable);
            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        };
    }

    suspend fun addNewRecipe(recipe: ScrapperRecipe) {
        //Check if the recipe already exists in the database, if so only add the user to the owners
        var rec = RecipeDatabase.getRecipeByID(recipe.id);
        if (rec != null) {
            if (!rec.owners_id.contains(user_id)) {
                rec.owners_id.add(user_id);
            }
        } else {
            //If the recipe does not exist yet
            rec = recipe.toDomainModel(this.user_id);
        }
        //rec.user_recipe_data = UserRecipeData(user_id);
        saveRecipe(rec)


        Log.e("AddRecipe", "Should be done by now");
    }

    suspend fun updateRecipe(recipe: ScrapperRecipe) {
        //Check if the recipe already exists, if so only add the user to the owners
        var rec = RecipeDatabase.getRecipeByID(recipe.id);
        if (rec != null) {
            if (!rec.owners_id.contains(user_id)) {
                Log.e("AddRecipe", "User doesn't have this recipe");
                TODO("Shouldn't be called like that");
            }
        }
        //If the recipe does not exist in the database yet
        else {
            rec = recipe.toDomainModel(this.user_id);
            //saveRecipe(new_rec)
        }

        saveRecipe(rec)
    }


    private fun saveRecipe(rec: Recipe) {
        RecipeDatabase.storeRecipe(rec);
        //if(rec.userData != null) {
        RecipeDatabase.storeUserRecipeData(rec, UserRecipeData(user_id));
        //}
    }

    fun setFavoriteRecipe(rec: UserRecipe, fav: Boolean) {
        if(rec.userData == null){
            rec.userData = UserRecipeData(user_id);
        }
        rec.userData!!.favorite = fav;
        RecipeDatabase.storeUserRecipeData(rec.recipe, rec.userData!!);
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