package com.aper_lab.grocery.liveData

import android.util.Log
import com.aper_lab.grocery.User
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.fragment.shoppingList.ShoppingListAdapter
import com.aper_lab.grocery.model.ShoppingList
import com.aper_lab.grocery.util.QuerySingleLiveMutableDataNative
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query

class LiveShoppingList(private val query: Query) : QuerySingleLiveMutableDataNative<ShoppingList>(query, ShoppingList::class.java) {

    override fun onEmpty() {
        super.onEmpty()
        value = ShoppingList(User.getInstance().user_id)
        RecipeDatabase.addShoppingList(value!!);
    }

    fun runWhenLoaded( a: (ShoppingList) -> Unit ){
        if(!this.first){
            this.value?.let{
                a(it)
            }
        }
        else{
            this.firstUpdate.observeForever(a);
        }
    }

}