package com.khaledsh.features.search.ui

import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.khaledsh.core.base.BaseFragment
import com.khaledsh.core.util.setPathParameter
import com.khaledsh.features.search.R
import com.khaledsh.features.search.databinding.FragmentSearchResultBinding
import com.khaledsh.features.search.ui.adapter.SearchLoadStateAdapter
import com.khaledsh.features.search.ui.adapter.SearchResultAdapter
import com.khaledsh.features.search.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding, SearchViewModel>() {

    private val navArgs: SearchResultFragmentArgs by navArgs()

    override fun getLayoutId(): Int = R.layout.fragment_search_result


    @Inject
    lateinit var mAdapter: SearchResultAdapter

    @Inject
    lateinit var loadStateAdapter: SearchLoadStateAdapter

    override fun onInitDataBinding() {
        viewBinding.viewModel = viewModel
        viewBinding.query = navArgs.query
    }

    override fun initView() = with(viewBinding) {
        recyclerViewSearchResult.apply {
            /** Set Decoration */
            val itemDecoration = DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
            itemDecoration.setDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.recyclerview_divider
                )!!
            )

            addItemDecoration(
                itemDecoration
            )

            retryButton.setOnClickListener {
                getData()
            }

            /** Set Adapter */
            adapter = mAdapter.withLoadStateFooter(loadStateAdapter)

            loadStateAdapter.retry = object : () -> Unit {
                override fun invoke() {
                    getData()
                }
            }
        }

        mAdapter.addLoadStateListener { loadState ->
            recyclerViewSearchResult.isVisible =
                loadState.source.refresh is LoadState.NotLoading
            progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            retryButton.isVisible = loadState.source.refresh is LoadState.Error
        }

        mAdapter.clickListener = {
            var uri = getString(com.khaledsh.core.R.string.profile_uri)
            uri = uri.setPathParameter("username", it.username)
            findNavController().navigate(Uri.parse(uri))
        }
    }


    override fun getData() {
        lifecycleScope.launch {
            viewModel.searchUsers(navArgs.query).distinctUntilChanged().collectLatest {
                mAdapter.submitData(it)
            }
        }

        viewModel.navigateToPreviousScreenEvent.observe(this) {
            findNavController().popBackStack()
        }
    }

}