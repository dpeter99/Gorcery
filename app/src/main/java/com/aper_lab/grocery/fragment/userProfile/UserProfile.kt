package com.aper_lab.grocery.fragment.userProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.aper_lab.grocery.FABFragment

import com.aper_lab.grocery.R
import com.aper_lab.grocery.databinding.FragmentUserProfileBinding

class UserProfile : FABFragment() {

    companion object {
        fun newInstance() =
            UserProfile()
    }

    private lateinit var viewModel: UserProfileViewModel

    private lateinit var binding: FragmentUserProfileBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = DataBindingUtil.inflate<FragmentUserProfileBinding>(inflater,
            R.layout.fragment_user_profile,container,false)

        fabParameters = null;


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)
        // TODO: Use the ViewModel

        binding.viewModel = viewModel;
        binding.signOut.setOnClickListener {
            viewModel.logOut();
        }
    }

}
