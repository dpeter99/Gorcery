package com.aper_lab.grocery.fragment.recipe.recipeMenu

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentRecipeBottomBinding
import com.aper_lab.grocery.fragment.recipe.RecipeViewModel
import com.aper_lab.grocery.fragment.recipe.RecipeViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RecipeMenuFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRecipeBottomBinding;

    private val viewModel: RecipeViewModel by viewModels<RecipeViewModel>({requireParentFragment()});

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentRecipeBottomBinding>(
            inflater,
            R.layout.fragment_recipe_bottom, container, false
        )
        binding.lifecycleOwner = this;

        //binding.root.layoutParams = ViewGroup.LayoutParams(200,200);

        //if(viewModel != null) {
            binding.viewModel = viewModel;
        //}


        val d:BottomSheetDialog = dialog as BottomSheetDialog;
        d.dismissWithAnimation = true;



        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setRecipeFavorite(buttonView.isChecked);
        }

        binding.deleteMenu.setOnClickListener {
            viewModel.removeRecipe();
            dismiss();
        }

        viewModel.recipe.observe(this, Observer {

            if(it.userData == null){
                binding.deleteMenu.visibility = View.GONE;
                binding.switch1.visibility = View.GONE;
            }


        })

        return binding.root;
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return super.onCreateDialog(savedInstanceState)
    }

}