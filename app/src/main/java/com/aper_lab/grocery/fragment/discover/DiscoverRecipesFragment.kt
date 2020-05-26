package com.aper_lab.grocery.fragment.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aper_lab.grocery.*

import com.aper_lab.grocery.databinding.FragmentDiscoverRecipesBinding
import com.aper_lab.grocery.fragment.UserProfileViewModel
import com.aper_lab.grocery.viewModel.recipe.RecipeViewModelFactory

class DiscoverRecipesFragment : FABFragment() {

    private lateinit var viewModel: DiscoverViewModel;
    //private lateinit var viewModelFactory: RecipeViewModelFactory

    private lateinit var binding: FragmentDiscoverRecipesBinding;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDiscoverRecipesBinding.inflate(inflater, container,false)

        //val args = RecipeFragmentArgs.fromBundle(arguments!!)

        //viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java);
        //viewModelFactory = RecipeViewModelFactory(args.recipeID)
        //viewModel = ViewModelProvider(this, viewModelFactory).get(RecipeViewModel::class.java);

        //binding.viewModel = viewModel;
        //binding.lifecycleOwner = this;
/*
        var profile_url = FirebaseAuth.getInstance().currentUser?.photoUrl;

        if(profile_url != null) {
            Glide.with(this)
                .load(profile_url)
                .into(binding.profileImage);
        }

 */

        binding.profileImage.setOnClickListener {
            findNavController().navigate(DiscoverRecipesFragmentDirections.actionDiscoverRecipesToUserProfile());
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)

        binding.viewModel = viewModel;

    }
}