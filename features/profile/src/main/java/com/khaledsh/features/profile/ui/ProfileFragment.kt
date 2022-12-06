package com.khaledsh.features.profile.ui

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.khaledsh.core.base.BaseFragment
import com.khaledsh.features.profile.R
import com.khaledsh.features.profile.databinding.FragmentProfileBinding
import com.khaledsh.features.profile.viewmodel.ProfileViewModel

class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    private val navArgs: ProfileFragmentArgs by navArgs()

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun initView() {
        viewBinding.toolbarProfile.setupWithNavController(findNavController())
    }

    override fun getData() {
        viewModel.fetchProfile(navArgs.username)

        viewModel.navigateToListFragmentEvent.observe(this) {
            val action =
                ProfileFragmentDirections.actionProfileFragmentToListFragment(it, navArgs.username)
            findNavController().navigate(action)
        }
    }

}