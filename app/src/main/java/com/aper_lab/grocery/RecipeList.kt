package com.aper_lab.grocery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.aper_lab.grocery.databinding.FragmentRecipeListBinding


/**
 * A simple [Fragment] subclass.
 * Use the [RecipeList.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeList : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentRecipeListBinding>(inflater,
            R.layout.fragment_recipe_list,container,false)

        //The complete onClickListener with Navigation
        binding.recipe1.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_recipeList_to_recepie)
        }

        return binding.root
    }
}
