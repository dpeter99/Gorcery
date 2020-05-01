package com.aper_lab.grocery.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.aper_lab.grocery.*

import com.aper_lab.grocery.databinding.FragmentRecipeListBinding
import com.aper_lab.grocery.viewModel.recipeList.RecipeListAdapter
import com.aper_lab.grocery.viewModel.recipeList.RecipeListViewModel
import com.aper_lab.grocery.viewModel.recipeList.RecipeListViewModelFactory
import com.aper_lab.scraperlib.data.Recipe
import com.google.android.material.bottomappbar.BottomAppBar


/**
 * A simple [Fragment] subclass.
 * Use the [RecipeList.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeList : Fragment() , IHasFAB {

    private lateinit var viewModel: RecipeListViewModel;

    private lateinit var fab: IFABProvider;

    private lateinit var recipeAdapter:RecipeListAdapter;

    private lateinit var binding: FragmentRecipeListBinding;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentRecipeListBinding>(inflater,
            R.layout.fragment_recipe_list,container,false)

        binding.lifecycleOwner = this;


        recipeAdapter = RecipeListAdapter(RecipeListAdapter.RecipeListener {
            viewModel.recipeClicked(it);
        });
        binding.recipeList.adapter = recipeAdapter;

        //Navigation event


/*
        binding.addButton.setOnClickListener {
            view?.findNavController()?.navigate(RecipeListDirections.actionRecipeListToAddRecipeFragment())
        }
*/
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, RecipeListViewModelFactory()).get(RecipeListViewModel::class.java);

        binding.viewModel = viewModel;

        // recipe list changes
        viewModel.recipes.observe(this.viewLifecycleOwner,Observer { recipe ->
            // Update the UI, in this case, a TextView.
            recipeAdapter.data = recipe?: mutableMapOf();
        })
        recipeAdapter.data = viewModel.recipes.value?: mutableMapOf<String,Recipe>();

        viewModel._recipeNav.observe(this.viewLifecycleOwner,Observer<String> { recipe ->
            view?.findNavController()?.navigate(
                RecipeListDirections.actionRecipeListToRecepie(
                    recipe
                )
            )
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is IFABProvider){
            fab = context;
            fab.setFABProperties( FABParameters(
                BottomAppBar.FAB_ALIGNMENT_MODE_END,
                R.drawable.ic_add_24dp
            ));
            fab.setFABListener(this);
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        fab.setFABProperties( FABParameters(
            BottomAppBar.FAB_ALIGNMENT_MODE_END,
            R.drawable.ic_add_24dp
        ));
        fab.setFABListener(this);
    }

    override fun onFABClicked() {
        view?.findNavController()?.navigate(RecipeListDirections.actionRecipeListToAddRecipeFragment())
    }
}
