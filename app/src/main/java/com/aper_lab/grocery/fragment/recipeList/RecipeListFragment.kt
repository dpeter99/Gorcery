package com.aper_lab.grocery.fragment.recipeList

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.aper_lab.grocery.util.FABUtils.FABFragment
import com.aper_lab.grocery.util.FABUtils.FABParameters
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentRecipeListBinding
import com.aper_lab.grocery.model.UserRecipe
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.lang.Exception


/**
 * A [Fragment] that is responsible for displaying the list of recipes hte user has.
 * It uses the [RecipeListViewModel] to display the list
 */
class RecipeListFragment : FABFragment() {

    private val args: RecipeListFragmentArgs by navArgs();

    private val viewModel: RecipeListViewModel by viewModels(factoryProducer = {RecipeListViewModel.provideFactory(args.tagFilter)});


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
        //viewModel = ViewModelProvider(this, RecipeListViewModelFactory()).get(RecipeListViewModel::class.java);
        binding.viewModel = viewModel;

        // recipe list changes
        viewModel.recipes.observe(this.viewLifecycleOwner,Observer { recipe ->
            // Update the UI, in this case, a TextView.
            recipeAdapter.data = recipe;
        })
        viewModel.recipes.value?.let {
            recipeAdapter.data = it;
        }


        viewModel._recipeNav.observe(this.viewLifecycleOwner,Observer<String> { recipe ->
            try {
                view?.findNavController()
                        ?.navigate(
                            RecipeListFragmentDirections.actionRecipeListToRecepie(
                                recipe
                            )
                        )
            }catch (e:Exception){
                FirebaseCrashlytics.getInstance().recordException(e)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_my_recipes, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onFABClicked() {
        view?.findNavController()?.navigate(RecipeListFragmentDirections.actionRecipeListToAddRecipeFragment(""))
    }
}
