package com.aper_lab.grocery.fragment.recipe.recipeMenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentRecipeBottomBinding
import com.aper_lab.grocery.fragment.recipe.RecipeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RecipeMenuFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRecipeBottomBinding;

    private lateinit var viewModel: RecipeViewModel;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentRecipeBottomBinding>(
            inflater,
            R.layout.fragment_recipe_bottom, container, false
        )
        binding.lifecycleOwner = this;

        //binding.root.layoutParams = ViewGroup.LayoutParams(200,200);

        if(viewModel != null) {
            binding?.viewModel = viewModel;
        }


        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setRecipeFavorite(buttonView.isChecked);
        }


        return binding.root;
    }

    fun show(manager: FragmentManager, tag : String, viewModel: RecipeViewModel) {
        this.viewModel = viewModel;



        super.show(manager,tag);
    }

}