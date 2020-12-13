package com.aper_lab.grocery.fragment.tags

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentUserTagsBinding
import com.aper_lab.grocery.fragment.tags.addTag.AddTagFragment
import com.aper_lab.grocery.model.RecipeTag
import com.aper_lab.grocery.util.FABUtils.FABFragment
import com.aper_lab.grocery.util.FABUtils.FABParameters
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale


class UserTagsFragment : FABFragment(), UserTagsAdapter.TagEventListener {

    lateinit var binding: FragmentUserTagsBinding;

    val viewModel: UserTagsViewModel by viewModels();

    val tagListAdapter = UserTagsAdapter(this);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserTagsBinding.inflate(inflater);

        binding.tagsList.adapter = tagListAdapter;

        fabParameters = FABParameters(
            BottomAppBar.FAB_ALIGNMENT_MODE_END,
            R.drawable.ic_add_24dp,
            null
        )

        // Inflate the layout for this fragment
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tags.value?.let {
            tagListAdapter.data = it;
        };
        viewModel.tags.observe(viewLifecycleOwner){
            it?.let {
                tagListAdapter.data = it;
            }
        }

        setFragmentResultListener(AddTagFragment.RESULT_ID){ s: String, bundle: Bundle ->
            val name = bundle.getString(AddTagFragment.RESULT_NAME);
            name?.let {
                viewModel.addNewTag(name);
            }

        }
    }

    override fun ontagClicked(tag: RecipeTag) {
        findNavController().navigate(UserTagsFragmentDirections.actionUserTagsToRecipeList(tag.name))
    }

    override fun onFABClicked() {
        findNavController().navigate(UserTagsFragmentDirections.actionUserTagsToAddTagFragment());
    }
}