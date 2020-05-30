package com.aper_lab.grocery.fragment.recipeList

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.aper_lab.grocery.FABFragment
import com.aper_lab.grocery.FABParameters
import com.aper_lab.grocery.IFABProvider
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentRecipeListBinding
import com.aper_lab.grocery.model.UserRecipe
import com.google.android.material.bottomappbar.BottomAppBar
import java.lang.Exception


/**
 * A [Fragment] that is responsible for displaying the list of recipes hte user has.
 * It uses the [RecipeListViewModel] to display the list
 */
class RecipeList : FABFragment() {

    private lateinit var viewModel: RecipeListViewModel;

    private lateinit var fab: IFABProvider;

    private lateinit var recipeAdapter: RecipeListAdapter;

    private lateinit var binding: FragmentRecipeListBinding;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentRecipeListBinding>(inflater,
            R.layout.fragment_recipe_list,container,false)
        binding.lifecycleOwner = this;

        setHasOptionsMenu(true);

        recipeAdapter =
            RecipeListAdapter(
                RecipeListAdapter.RecipeListener {
                    viewModel.recipeClicked(it);
                });
        binding.recipeList.adapter = recipeAdapter;

        fabParameters = FABParameters(
            BottomAppBar.FAB_ALIGNMENT_MODE_END,
            R.drawable.ic_add_24dp,
            null
        );

        //Navigation event
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,
            RecipeListViewModelFactory()
        ).get(RecipeListViewModel::class.java);
        binding.viewModel = viewModel;

        // recipe list changes
        viewModel.recipes.observe(this.viewLifecycleOwner,Observer { recipe ->
            // Update the UI, in this case, a TextView.
            recipeAdapter.data = recipe?: mutableMapOf();
        })
        recipeAdapter.data = viewModel.recipes.value?: mutableMapOf<String,UserRecipe>();

        viewModel._recipeNav.observe(this.viewLifecycleOwner,Observer<String> { recipe ->
            try {
                view?.findNavController()
                        ?.navigate(
                            RecipeListDirections.actionRecipeListToRecepie(
                                recipe
                            )
                        )
            }catch (e:Exception){

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_my_recipes, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onFABClicked() {
        view?.findNavController()?.navigate(RecipeListDirections.actionRecipeListToAddRecipeFragment(""))
    }
}
