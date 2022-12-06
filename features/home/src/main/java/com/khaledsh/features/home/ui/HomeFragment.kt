package com.khaledsh.features.home.ui

import android.net.Uri
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.khaledsh.core.base.BaseFragment
import com.khaledsh.core.util.setPathParameter
import com.khaledsh.features.home.R
import com.khaledsh.features.home.databinding.FragmentHomeBinding
import com.khaledsh.features.home.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        viewBinding.editTextHomeSearchQuery.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var uri = getString(com.khaledsh.core.R.string.search_uri)
                uri = uri.setPathParameter("query", textView.text.toString())
                findNavController().navigate(Uri.parse(uri))
                textView.text = null
                true
            } else false
        }
    }

    override fun getData() {
    }
}
