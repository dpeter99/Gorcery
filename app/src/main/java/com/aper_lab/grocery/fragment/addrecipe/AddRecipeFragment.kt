package com.aper_lab.grocery.fragment.addrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.aper_lab.grocery.*
import com.aper_lab.grocery.databinding.FragmentAddRecipeBinding
import com.aper_lab.scraperlib.RecipeAPIService
import com.aper_lab.scraperlib.RecipeAPIService.scope
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recipe_import_preview.*
import kotlinx.coroutines.launch


class AddRecipeFragment : FABFragment() {

    companion object {
        fun newInstance() =
            AddRecipeFragment()
    }


    private lateinit var viewModel: AddRecipeViewModel

    private lateinit var binding: FragmentAddRecipeBinding
    private lateinit var preview: View;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_recipe, container, false)
        binding.setLifecycleOwner(this);

        binding.urlInput.editText?.setText(arguments?.getString("url"));
        binding.urlInput.getEditText()?.doOnTextChanged { text, start, before, count ->
            viewModel.urlChanged();
        }


        preview = binding.root.findViewById(R.id.recipe_import_preview);

        fabParameters = FABParameters(
            BottomAppBar.FAB_ALIGNMENT_MODE_END,
            R.drawable.ic_search_24dp,
            null
        )



        return binding.root;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, AddRecipeViewModelFactory()).get(AddRecipeViewModel::class.java)

        binding.viewModel = viewModel;

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                AddRecipeViewModel.State.Default -> {
                    fabParameters = FABParameters(
                        BottomAppBar.FAB_ALIGNMENT_MODE_END,
                        R.drawable.ic_search_24dp,
                        null
                    )
                    preview.visibility = View.INVISIBLE;
                    binding.progressBar.visibility = View.GONE;
                    binding.instructions.visibility = View.VISIBLE;
                }
                AddRecipeViewModel.State.Loading -> {
                    fabParameters = null;
                    binding.progressBar.visibility = View.VISIBLE;
                    binding.instructions.visibility = View.GONE;
                }
                AddRecipeViewModel.State.Done -> {
                    fabParameters = FABParameters(
                        BottomAppBar.FAB_ALIGNMENT_MODE_END,
                        R.drawable.ic_done_24dp,
                        null
                    )

                    preview.visibility = View.VISIBLE;
                    binding.progressBar.visibility = View.GONE;
                    binding.instructions.visibility = View.GONE;
                }

            }
            binding.executePendingBindings();
        })

        viewModel.navigateToRecipe.observe(viewLifecycleOwner, Observer {
            if (viewModel.recipe.value != null) {
                view?.findNavController()?.navigate(
                    AddRecipeFragmentDirections.actionAddRecipeFragmentToRecepie(
                        viewModel.recipe.value?.id ?: ""
                    )
                )
            }
        })

        val url =arguments?.getString("url");
        if(!url.isNullOrBlank()){
            viewModel.getRecipeFromURL(url);
        }
    }

    override fun onFABClicked() {
        if (viewModel.state.value == AddRecipeViewModel.State.Done) {
            viewModel.saveRecipe();

        } else {
            val url: String = binding.urlInput.getEditText()?.getText().toString();
            if(!url.isNullOrBlank()) {
                viewModel.getRecipeFromURL(url);
            }
        }
    }

}
