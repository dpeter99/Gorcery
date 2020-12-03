package com.aper_lab.grocery.fragment.shoppingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.aper_lab.grocery.FABFragment
import com.aper_lab.grocery.FABParameters
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentShoppingListBinding
import com.aper_lab.grocery.model.ShoppingItem
import com.aper_lab.grocery.viewModel.ShoppingListViewModel
import com.google.android.material.bottomappbar.BottomAppBar

class ShoppingListView : FABFragment(), ShoppingListAdapter.ShoppingListListener {

    private lateinit var viewModel: ShoppingListViewModel;
    //private lateinit var viewModelFactory: ShoppingListBinding;

    private lateinit var binding: FragmentShoppingListBinding;

    private lateinit var shoppingListAdapter_uncehcked: ShoppingListAdapter;
    private lateinit var shoppingListAdapter_cehcked: ShoppingListAdapter;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_list, container, false)
        binding.lifecycleOwner = this;

        fabParameters = FABParameters(
            BottomAppBar.FAB_ALIGNMENT_MODE_END,
            R.drawable.ic_add_24dp,
            null
        );

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val name = bundle.getString("name")
            val amount = bundle.getString("amount") ?: "0"
            // Do something with the result
            viewModel.AddShoppingListItem(ShoppingItem(name, amount.toFloat()));
        }

        return binding.root;
    }

    override fun onFABClicked() {
        super.onFABClicked()

        //

        view?.findNavController()
                ?.navigate(
                    ShoppingListViewDirections.actionShoppingListToAddNewShoppingItem()
                )


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ShoppingListViewModel::class.java)
        binding.viewModel = viewModel;

        shoppingListAdapter_uncehcked = ShoppingListAdapter(this, false);
        binding.shoppingListRecycleView.adapter = shoppingListAdapter_uncehcked;

        shoppingListAdapter_cehcked = ShoppingListAdapter(this, true);
        binding.shoppingListChecked.adapter = shoppingListAdapter_cehcked;

        viewModel.shoppingList.observe(this.viewLifecycleOwner, Observer {
            shoppingListAdapter_uncehcked.data = it;
            shoppingListAdapter_cehcked.data = it;

            val checked = it.items.any { it -> it.checked }
            if (checked) {
                binding.checkedItemsText.visibility = View.VISIBLE
            } else {
                binding.checkedItemsText.visibility = View.INVISIBLE
            }

        })
    }

    override fun onShoppingItemClick(recipe: ShoppingItem) {
        viewModel.CheckListItem(recipe);
    }
}