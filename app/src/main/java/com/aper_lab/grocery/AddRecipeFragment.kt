package com.aper_lab.grocery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aper_lab.grocery.databinding.FragmentAddRecipeBinding


class AddRecipeFragment : Fragment() {

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



        binding.btnImport.setOnClickListener {
            viewModel.importRecipe(binding.recipeUrl.text.toString());
            Log.e("Test", viewModel.successfulImport.value.toString())
        }



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddRecipeViewModel::class.java)

        binding.viewModel = viewModel;

        // Create the observer which updates the UI.
        val navObserver = Observer<Boolean> { recipe ->
            // Update the UI, in this case, a TextView.
        }
        viewModel.successfulImport.observe(this.viewLifecycleOwner,navObserver)
    }

}
