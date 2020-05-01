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
import com.google.android.material.bottomappbar.BottomAppBar


class AddRecipeFragment : FABFragment() {

    companion object {
        fun newInstance() =
            AddRecipeFragment()
    }

    private lateinit var viewModel: AddRecipeViewModel

    lateinit var binding :FragmentAddRecipeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_recipe,container,false)
        binding.setLifecycleOwner(this);

        binding.urlInput.getEditText()?.doOnTextChanged { text, start, before, count ->
            viewModel.urlChanged();
        }

        fabParameters = FABParameters(
            BottomAppBar.FAB_ALIGNMENT_MODE_END,
            R.drawable.ic_search_24dp
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, AddRecipeViewModelFactory()).get(AddRecipeViewModel::class.java)

        binding.viewModel = viewModel;

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when(it) {
                AddRecipeViewModel.State.Default ->
                    fabParameters = FABParameters(
                        BottomAppBar.FAB_ALIGNMENT_MODE_END,
                        R.drawable.ic_search_24dp
                    )
                AddRecipeViewModel.State.Done ->
                    fabParameters = FABParameters(
                        BottomAppBar.FAB_ALIGNMENT_MODE_END,
                        R.drawable.ic_done_24dp
                    )
            }
        })
    }

    override fun onFABClicked() {
        Toast.makeText(context,"asd",Toast.LENGTH_SHORT).show();
        if(viewModel.state.value == AddRecipeViewModel.State.Done) {
            viewModel.saveRecipe();
            if(viewModel.recipe.value != null) {
                view?.findNavController()?.navigate(
                    AddRecipeFragmentDirections.actionAddRecipeFragmentToRecepie(
                        viewModel.recipe.value?.id ?: ""
                    )
                )
            }
        }
        else{
            var url :String = binding.urlInput.getEditText()?.getText().toString();

            viewModel.getRecipeFromURL(url);
        }
    }

}
