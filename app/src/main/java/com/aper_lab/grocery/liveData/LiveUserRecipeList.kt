package com.aper_lab.grocery.liveData

import com.aper_lab.grocery.User
import com.aper_lab.grocery.model.Recipe
import com.aper_lab.grocery.model.UserRecipe
import com.aper_lab.grocery.model.UserRecipeData
import com.aper_lab.grocery.util.QueryLiveDataList
import com.aper_lab.grocery.UserData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

class LiveUserRecipeList(query:Query): QueryLiveDataList<UserRecipe>(query) {


    override fun parseSingle(item: DocumentSnapshot): UserRecipe {

        //val a = LiveUserRecipe(item.reference);

        val r = item.toObject<Recipe>();

        var userRecipe:  UserRecipe? = null;

        r?.let {

            val userData = item.reference.collection("/owners")
                    .document(UserData.instance.user_id)
            userRecipe = UserRecipe(it, null);

            userData.addSnapshotListener { value, error ->

                value?.let {
                    val ID = it.reference.parent.parent?.id

                    val parent_recipe = this.value?.find { it.recipe.id == ID }

                    parent_recipe?.userData = value.toObject<UserRecipeData>();

                    this.postValue(this.value);
                }

            }

        };

        return userRecipe!!;
    }
/*
    override fun parseSnapshot(querySnapshot: QuerySnapshot): Map<String, LiveUserRecipe> {
        return querySnapshot.documents.map { parseSingle(it) }.toMap();
    }
*/

}
