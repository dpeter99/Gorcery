package com.aper_lab.grocery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.aper_lab.grocery.databinding.FragmentRecipeListBinding
import com.aper_lab.grocery.viewModel.recipeList.RecipeListAdapter
import com.aper_lab.grocery.viewModel.recipeList.RecipeListViewModel
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.data.Recipe


/**
 * A simple [Fragment] subclass.
 * Use the [RecipeList.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeList : Fragment() {

    private lateinit var viewModel: RecipeListViewModel;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentRecipeListBinding>(inflater,
            R.layout.fragment_recipe_list,container,false)

        viewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java);

        binding.lifecycleOwner = this;
        binding.viewModel = viewModel;

        val recipeAdapter = RecipeListAdapter(RecipeListAdapter.RecipeListener {
            //Toast.makeText(context, "${it}", Toast.LENGTH_LONG).show();
            //view?.findNavController()?.navigate(R.id.action_recipeList_to_recepie)
            viewModel.recipeClicked();
        });
        binding.recipeList.adapter = recipeAdapter;



        // Create the observer which updates the UI.
        val recipeObserver = Observer<List<Recipe>> { recipe ->
            // Update the UI, in this case, a TextView.
            recipeAdapter.data = recipe?: listOf<Recipe>();
        }
        viewModel.recipesLiveData.observe(this.viewLifecycleOwner,recipeObserver)

        // Create the observer which updates the UI.
        val navObserver = Observer<Recipe> { recipe ->
            // Update the UI, in this case, a TextView.
            view?.findNavController()?.navigate(RecipeListDirections.actionRecipeListToRecepie(recipe.id))
        }
        viewModel._recipeNav.observe(this.viewLifecycleOwner,navObserver)

        binding.addButton.setOnClickListener {
            view?.findNavController()?.navigate(RecipeListDirections.actionRecipeListToAddRecipeFragment())
        }

        return binding.root
    }
}
