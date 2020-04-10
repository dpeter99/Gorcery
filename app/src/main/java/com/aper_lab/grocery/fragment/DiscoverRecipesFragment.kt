package com.aper_lab.grocery.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aper_lab.grocery.R

import com.aper_lab.grocery.databinding.FragmentDiscoverRecipesBinding
import com.aper_lab.grocery.databinding.FragmentRecepieBinding
import com.aper_lab.grocery.viewModel.recipe.RecipeDirectionsAdapter
import com.aper_lab.grocery.viewModel.recipe.RecipeIngredientAdapter
import com.aper_lab.grocery.viewModel.recipe.RecipeViewModel
import com.aper_lab.grocery.viewModel.recipe.RecipeViewModelFactory
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep

class DiscoverRecipesFragment : Fragment() {

    private lateinit var viewModel: RecipeViewModel;
    private lateinit var viewModelFactory: RecipeViewModelFactory


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDiscoverRecipesBinding.inflate(inflater, container,false)

        //val args = RecipeFragmentArgs.fromBundle(arguments!!)

        //viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java);
        //viewModelFactory = RecipeViewModelFactory(args.recipeID)
        //viewModel = ViewModelProvider(this, viewModelFactory).get(RecipeViewModel::class.java);

        //binding.viewModel = viewModel;
        //binding.lifecycleOwner = this;

        return binding.root
    }

}