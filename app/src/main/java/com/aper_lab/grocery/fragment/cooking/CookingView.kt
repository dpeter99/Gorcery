package com.aper_lab.grocery.fragment.cooking

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentCookingViewBinding
import com.aper_lab.grocery.databinding.FragmentDiscoverRecipesBinding
import com.aper_lab.grocery.fragment.recipe.RecipeFragmentArgs
import com.aper_lab.grocery.fragment.recipeList.RecipeListAdapter
import com.aper_lab.scraperlib.data.RecipeStep
import com.google.android.material.tabs.TabLayoutMediator

class CookingView : Fragment() {

    private lateinit var recipeID: String;

    private lateinit var viewModel: CookingViewViewModel;

    private lateinit var binding: FragmentCookingViewBinding;

    private lateinit var adapter: CookingPageAdapter;
    private lateinit var adapter_page: CookingPageTabAdapter;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCookingViewBinding.inflate(inflater, container,false)

        recipeID = CookingViewArgs.fromBundle(
            requireArguments()
        ).recipeId

        adapter = CookingPageAdapter()


        binding.viewPager.adapter = adapter;
        /*
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setCustomView(R.layout.cooking_tab);
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
        */
        adapter_page = CookingPageTabAdapter(
            CookingPageTabAdapter.TabListener {
            binding.viewPager.setCurrentItem(it-1,false);
        });
        binding.tabLayout.adapter = adapter_page;

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, CookingViewViewModel.ViewModelFactory(recipeID)).get(CookingViewViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.recipe.observe(this.viewLifecycleOwner, Observer {
            adapter.data = it.recipe.directions;
            adapter_page.data = it.recipe.directions;
        })
    }

}