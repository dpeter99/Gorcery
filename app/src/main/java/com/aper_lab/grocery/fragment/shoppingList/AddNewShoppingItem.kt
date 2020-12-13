package com.aper_lab.grocery.fragment.shoppingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import com.aper_lab.grocery.R
import androidx.fragment.app.setFragmentResult
import com.aper_lab.grocery.util.FABUtils.FABFragment
import com.aper_lab.grocery.util.FABUtils.FABParameters
import com.aper_lab.grocery.databinding.FragmentAddNewShoppingItemBinding
import com.google.android.material.bottomappbar.BottomAppBar

class AddNewShoppingItem : FABFragment() {

    private lateinit var binding: FragmentAddNewShoppingItemBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fabParameters = FABParameters(
            BottomAppBar.FAB_ALIGNMENT_MODE_END,
            R.drawable.ic_done_24dp,
            null
        );

    }

    override fun onFABClicked() {
        super.onFABClicked()

        var newItem = binding.itemNameInput.text.toString();
        val amount = binding.productAmount.text.toString();
        setFragmentResult("requestKey", bundleOf("name" to newItem, "amount" to amount))

        parentFragmentManager.popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_shopping_item, container, false)
        binding.lifecycleOwner = this;

        //val result = "result"
        //setFragmentResult("requestKey", bundleOf("bundleKey" to result))

        // Inflate the layout for this fragment
        return binding.root;
    }

}