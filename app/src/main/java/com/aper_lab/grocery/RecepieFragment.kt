package com.aper_lab.grocery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aper_lab.grocery.databinding.FragmentRecepieBinding
import com.aper_lab.grocery.viewModel.recipe.RecipeDirectionsAdapter
import com.aper_lab.grocery.viewModel.recipe.RecipeIngredientAdapter
import com.aper_lab.grocery.viewModel.recipe.RecipeViewModel
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.Recipe
import com.aper_lab.scraperlib.data.RecipeStep


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
        binding.lifecycleOwner = this;

        var adapter_ingredients = RecipeIngredientAdapter();
        adapter_ingredients.data = viewModel.recipe.value?.ingredients ?: listOf(Ingredient("",""));
        binding.ingredientList.adapter = adapter_ingredients;

        var adapter_directions = RecipeDirectionsAdapter();
        adapter_directions.data = viewModel.recipe.value?.directions ?: listOf(RecipeStep(0,""));
        binding.stepsList.adapter = adapter_directions;

        // Create the observer which updates the UI.
        val recipeObserver = Observer<Recipe> { recipe ->
            // Update the UI, in this case, a TextView.
            adapter_directions.data = recipe.directions
        }

        viewModel.recipe.observe(this.viewLifecycleOwner,recipeObserver)



        return binding.root
    }

}