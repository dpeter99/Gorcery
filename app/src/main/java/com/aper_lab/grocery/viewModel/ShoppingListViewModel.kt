package com.aper_lab.grocery.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aper_lab.grocery.User
import com.aper_lab.grocery.database.RecipeDatabase
import com.aper_lab.grocery.liveData.LiveShoppingList
import com.aper_lab.grocery.model.Recipe
import com.aper_lab.grocery.model.ShoppingItem
import com.aper_lab.grocery.model.ShoppingList
import com.aper_lab.grocery.model.UserRecipe
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.datastore.DataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingListViewModel : ViewModel() {
    var shoppingList: LiveShoppingList = RecipeDatabase.getShoppingList();



    init {


    }

    fun AddShoppingListItem(item: ShoppingItem) {
        shoppingList.let {
            it.value?.items?.add(item)
            RecipeDatabase.updateShoppingList(it)
        }
    }

    fun CheckListItem(item: ShoppingItem) {
        //shoppingList.value?.items?.find { it == item }?.checked?.not()
        item.checked = !item.checked;
        RecipeDatabase.updateShoppingList(shoppingList)
    }


}