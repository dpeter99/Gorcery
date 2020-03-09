package com.aper_lab.grocery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.aper_lab.grocery.databinding.FragmentRecepieBinding
import com.aper_lab.grocery.viewModel.recipe.RecipeDirectionsAdapter
import com.aper_lab.grocery.viewModel.recipe.RecipeIngredientAdapter
import com.aper_lab.grocery.viewModel.recipe.RecipeViewModel

/**
 * A simple [Fragment] subclass.
 */
class RecepieFragment : Fragment() {

    private lateinit var viewModel: RecipeViewModel;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentRecepieBinding>(inflater, R.layout.fragment_recepie,container,false)

        //viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java);
        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java);

        binding.viewModel = viewModel;

        var adapter_ingredients = RecipeIngredientAdapter();
        adapter_ingredients.data = viewModel.recipe.ingredients;
        binding.ingredientList.adapter = adapter_ingredients;

        var adapter_directions = RecipeDirectionsAdapter();
        adapter_directions.data = viewModel.recipe.directions;
        binding.stepsList.adapter = adapter_directions;


        return binding.root
    }

}
