package com.aper_lab.grocery.fragment.recipe

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aper_lab.grocery.FABFragment
import com.aper_lab.grocery.FABParameters
import com.aper_lab.grocery.IFABProvider
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentRecepieBinding
import com.aper_lab.grocery.fragment.recipe.recipeMenu.RecipeMenuFragment
import com.aper_lab.scraperlib.data.Ingredient
import com.aper_lab.scraperlib.data.RecipeStep
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.crashlytics.FirebaseCrashlytics


/**
 * A simple [Fragment] subclass.
 */
class RecipeFragment : FABFragment() {

    private lateinit var viewModel: RecipeViewModel;
    private lateinit var viewModelFactory: RecipeViewModelFactory;

    private lateinit var binding: FragmentRecepieBinding;
    private lateinit var recipeID: String;

    private lateinit var adapter_ingredients : RecipeIngredientAdapter;
    private lateinit var adapter_steps : RecipeDirectionsAdapter;

    private lateinit var bottom_sheet_behav: BottomSheetBehavior<CardView?>;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRecepieBinding>(
            inflater,
            R.layout.fragment_recepie, container, false
        )
        binding.lifecycleOwner = this;

        setHasOptionsMenu(true);

        recipeID = RecipeFragmentArgs.fromBundle(
            requireArguments()
        ).recipeID

        adapter_ingredients =
            RecipeIngredientAdapter();
        binding.ingredientList.adapter = adapter_ingredients;

        adapter_steps =
            RecipeDirectionsAdapter();
        binding.stepsList.adapter = adapter_steps;

        binding.toCooking.setOnClickListener {
            val navController = findNavController()
            try {
                navController.navigate(RecipeFragmentDirections.actionRecepieToCookingView(recipeID))
            }catch (e:Exception){
                Log.e("App","There was a problem with navigation")
                Log.e("App", "Current navgraph: " + navController.graph.toString())
                e.printStackTrace();
                FirebaseCrashlytics.getInstance().recordException(e)
            }

        }

        val toggleButton: ToggleButton
        toggleButton = binding.favCheckbox
        toggleButton.isChecked = false
        toggleButton.setBackgroundResource( R.drawable.ic_heart_dark_24dp);
        toggleButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) toggleButton.setBackgroundResource(R.drawable.ic_heart_24dp)
            else toggleButton.setBackgroundResource(R.drawable.ic_heart_dark_24dp)
            viewModel.setRecipeFavorite(isChecked);
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            RecipeViewModelFactory(recipeID)
        ).get(RecipeViewModel::class.java);
        binding.viewModel = viewModel;

        adapter_ingredients.data = viewModel.recipe.value?.recipe?.ingredients ?: listOf(Ingredient("", ""));
        adapter_steps.data = viewModel.recipe.value?.recipe?.directions ?: listOf(RecipeStep(0, ""));

        viewModel.recipe.observe(this.viewLifecycleOwner, Observer { recipe ->
            // Update the UI, in this case, a TextView.
            adapter_steps.data = recipe.recipe.directions
            adapter_ingredients.data = recipe.recipe.ingredients

            if(recipe.userData == null){
                binding.toCooking.visibility = View.INVISIBLE;
                binding.favCheckbox.visibility =View.INVISIBLE;
                binding.favCheckbox.isChecked = recipe.userData!!.favorite;
                fabParameters = FABParameters(
                    BottomAppBar.FAB_ALIGNMENT_MODE_END,
                    R.drawable.ic_notebook_plus_24dp,
                    null
                )
            }
            else{
                binding.toCooking.visibility = View.VISIBLE;
                binding.favCheckbox.visibility =View.VISIBLE;
                fabParameters = null;
            }

        })

        binding.recipeSourceLink.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(viewModel.recipe.value?.recipe?.link?:""));
            startActivity(i);
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_recipe,menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.recipe_menu -> {
                val bottomSheet = RecipeMenuFragment()
                bottomSheet.show(childFragmentManager, "exampleBottomSheet")
                //bottom_sheet_behav.state = BottomSheetBehavior.STATE_EXPANDED;
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is IFABProvider) {
            context.setFABProperties(null);
            context.setFABListener(this);
        }
    }

    override fun onFABClicked() {
        viewModel.addRecipeToCollection();
        Toast.makeText(this.context,"Successfully added recipe",Toast.LENGTH_LONG).show();
    }
}