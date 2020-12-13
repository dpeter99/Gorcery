package com.aper_lab.grocery

import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.liveData.LiveUser
import com.aper_lab.grocery.liveData.LiveUserRecipeList
import com.google.firebase.auth.FirebaseAuth

class UserData {

    lateinit var user: LiveUser;

    lateinit private var _recipes: LiveUserRecipeList;
    val recipes: LiveUserRecipeList
        get() = _recipes;


    val user_id:String
    get() = FirebaseAuth.getInstance().currentUser?.uid ?: ""



    init {
        if(_instance == null)
            _instance = this;

        if (FirebaseAuth.getInstance().currentUser == null) {
            FirebaseAuth.AuthStateListener { auth ->
                auth.currentUser?.let {
                    user = RecipeDatabase.getUser(it.uid)
                    _recipes = RecipeDatabase.getLiveUserRecipesCollection();
                }
            }
        }
        FirebaseAuth.getInstance().currentUser?.let {
            user = RecipeDatabase.getUser(it.uid)
            _recipes = RecipeDatabase.getLiveUserRecipesCollection();
        }

    }

    companion object{
        private var _instance : UserData? = null;

        val instance : UserData
        get() {
            if(_instance == null){
                _instance = UserData();
            }
            return _instance!!;
        }
    }
}


