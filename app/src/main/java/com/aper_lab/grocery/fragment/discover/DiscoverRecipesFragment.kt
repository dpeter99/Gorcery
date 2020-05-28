package com.aper_lab.grocery.fragment.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.aper_lab.grocery.FABFragment
import com.aper_lab.grocery.MainActivity
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentDiscoverRecipesBinding
import com.aper_lab.grocery.fragment.recipeList.RecipeListDirections
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DiscoverRecipesFragment : FABFragment() {

    private lateinit var viewModel: DiscoverRecipesViewModel;
    //private lateinit var viewModelFactory: RecipeViewModelFactory

    private lateinit var binding: FragmentDiscoverRecipesBinding;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDiscoverRecipesBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = this;



        binding.navMenuButton.setOnClickListener {
            (activity as MainActivity?)!!.openCloseNavigationDrawer();
        }

        binding.profileImage.setOnClickListener {
            findNavController().navigate(DiscoverRecipesFragmentDirections.actionDiscoverRecipesToUserProfile());
        }

        binding.mainRecipe.main.setOnClickListener {
            viewModel.openMainRecipe();
        }

        binding.secondaryRecipe.main.setOnClickListener {
            viewModel.openSecondaryRecipe(0);
        }

        binding.refreshLayout.setColorSchemeResources(R.color.primaryColor)
        binding.refreshLayout.setOnRefreshListener {
            GlobalScope.launch {
                viewModel.refresRecipesAsync();
                binding.refreshLayout.setRefreshing(false);
            }
        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DiscoverRecipesViewModel::class.java)

        viewModel._recipeNav.observe(this.viewLifecycleOwner,Observer<String> { recipe ->
            view?.findNavController()?.navigate(
                DiscoverRecipesFragmentDirections.actionDiscoverRecipesToRecepie(
                    recipe
                )
            )
        })

        binding.viewModel = viewModel;

    }
}