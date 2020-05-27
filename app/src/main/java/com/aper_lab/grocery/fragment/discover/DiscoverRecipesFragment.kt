package com.aper_lab.grocery.fragment.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aper_lab.grocery.FABFragment
import com.aper_lab.grocery.MainActivity
import com.aper_lab.grocery.databinding.FragmentDiscoverRecipesBinding


class DiscoverRecipesFragment : FABFragment() {

    private lateinit var viewModel: DiscoverViewModel;
    //private lateinit var viewModelFactory: RecipeViewModelFactory

    private lateinit var binding: FragmentDiscoverRecipesBinding;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDiscoverRecipesBinding.inflate(inflater, container,false)

        binding.navMenuButton.setOnClickListener {
            (activity as MainActivity?)!!.openCloseNavigationDrawer();
        }

        binding.profileImage.setOnClickListener {
            findNavController().navigate(DiscoverRecipesFragmentDirections.actionDiscoverRecipesToUserProfile());
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
        viewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)

        binding.viewModel = viewModel;

    }
}