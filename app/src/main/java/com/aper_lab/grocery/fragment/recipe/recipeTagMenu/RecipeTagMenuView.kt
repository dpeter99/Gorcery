package com.aper_lab.grocery.fragment.recipe.recipeTagMenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.aper_lab.grocery.util.FABUtils.FABFragment
import com.aper_lab.grocery.databinding.FragmentRecipeTagMenuBinding

class RecipeTagMenuView : FABFragment(), RecipeTagAdapter.RecipeTagEventListener {

    val args: RecipeTagMenuViewArgs by navArgs()

    private lateinit var binding: FragmentRecipeTagMenuBinding;

    private val viewModel: RecipeTagMenuViewModel by viewModels(factoryProducer = { RecipeTagMenuViewModel.provideFactory(args.recipeID) });

    private var listAdapter: RecipeTagAdapter = RecipeTagAdapter(this);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRecipeTagMenuBinding.inflate(inflater)


        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = ViewModelProvider(this).get(UserModel::class.java)

        //listAdapter = RecipeTagAdapter();

        viewModel.tags.value?.let {
            listAdapter.data = it;
        };

        viewModel.tags.observe(viewLifecycleOwner,{
            it?.let {
                listAdapter.data = it;
            }

        })
        
        binding.recyclerView.adapter = listAdapter;
    }

    override fun onTagCheckChanged(tag: String, state: Boolean) {
        viewModel.setTag(tag,state)
    }
}