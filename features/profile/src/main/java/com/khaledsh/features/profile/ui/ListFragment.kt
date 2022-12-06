package com.khaledsh.features.profile.ui

import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import com.khaledsh.core.base.BaseFragment
import com.khaledsh.features.profile.R
import com.khaledsh.features.profile.databinding.FragmentListBinding
import com.khaledsh.features.profile.ui.adapter.ListAdapter
import com.khaledsh.features.profile.ui.adapter.ListLoadStateAdapter
import com.khaledsh.features.profile.viewmodel.ListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class ListFragment : BaseFragment<FragmentListBinding, ListViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    private val navArgs: ListFragmentArgs by navArgs()

    @Inject
    lateinit var listAdapter: ListAdapter

    @Inject
    lateinit var loadStateAdapter: ListLoadStateAdapter

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.type = navArgs.type
        viewBinding.recyclerViewData.adapter = listAdapter.withLoadStateFooter(loadStateAdapter)

        loadStateAdapter.retry = object : () -> Unit {
            override fun invoke() {
                getData()
            }
        }

        listAdapter.addLoadStateListener { loadState ->
            viewBinding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
        }
    }

    override fun initView() {
        viewBinding.toolbarListFragment.setupWithNavController(findNavController())
    }

    override fun getData() {
        lifecycleScope.launch {
            viewModel.getData(navArgs.type, navArgs.username).collectLatest {
                listAdapter.submitData(it)
            }
        }
    }
}
