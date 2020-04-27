package com.aper_lab.grocery.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_add_recipe.view.*


class AddRecipeFragment : FABFragment() {

    companion object {
        fun newInstance() = AddRecipeFragment()
    }

    private lateinit var viewModel: AddRecipeViewModel

    lateinit var binding :FragmentAddRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAddRecipeBinding>(inflater,
            R.layout.fragment_add_recipe,container,false)

        fabParameters = FABParameters(
            BottomAppBar.FAB_ALIGNMENT_MODE_END,
            R.drawable.ic_search_24dp
        )

        binding.urlInput.getEditText()?.doOnTextChanged { text, start, before, count ->
            viewModel.urlChanged();
        }

        binding.setLifecycleOwner(this);

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddRecipeViewModel::class.java)

        binding.viewModel = viewModel;

        // Create the observer which updates the UI.
/*
        val navObserver = Observer<Boolean> { it ->
            // Update the UI, in this case, a TextView.
            binding.materialCardView.visibility = if(it){
                View.VISIBLE;
            } else {
                View.INVISIBLE;
            }
        }
        viewModel.successfulImport.observe(this.viewLifecycleOwner,navObserver)
 */
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
