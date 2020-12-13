package com.aper_lab.grocery.fragment.tags.addTag

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.transition.Slide
import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentAddTagBinding
import com.aper_lab.grocery.util.FABUtils.FABFragment
import com.aper_lab.grocery.util.FABUtils.FABParameters
import com.aper_lab.grocery.util.getColorFromAttr
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.transition.MaterialContainerTransform


class AddTagFragment : FABFragment() {

    lateinit var binding: FragmentAddTagBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddTagBinding.inflate(inflater);

        fabParameters = FABParameters(
            BottomAppBar.FAB_ALIGNMENT_MODE_END,
            R.drawable.ic_done_24dp,
            null
        )

        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.fab)
            endView = binding.root
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            containerColor = requireContext().getColorFromAttr(R.attr.colorSurface)
            startContainerColor = requireContext().getColorFromAttr(R.attr.colorSecondary)
            endContainerColor = requireContext().getColorFromAttr(R.attr.colorSurface)
            //this.endElevation = -10f
            //this.startElevation = -10f
        }

        returnTransition = Slide().apply {
            duration = resources.getInteger(R.integer.motion_duration_medium).toLong()
            addTarget(binding.root)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onFABClicked() {
        val name = binding.nameInput.text.toString();

        if(name.isNotBlank()){
            this.setFragmentResult(RESULT_ID, bundleOf(RESULT_NAME to name));
            parentFragmentManager.popBackStack()
        }
    }


    companion object{
        final val RESULT_ID = "addNewTag"
        final val RESULT_NAME = "name"
    }
}