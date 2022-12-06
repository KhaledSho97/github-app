package com.khaledsh.features.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khaledsh.features.search.databinding.ViewholderLoadStateFooterBinding
import javax.inject.Inject

class SearchLoadStateAdapter @Inject constructor() :
    LoadStateAdapter<SearchLoadStateAdapter.ViewHolder>() {

    var retry: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = ViewholderLoadStateFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)


    class ViewHolder(
        private val binding: ViewholderLoadStateFooterBinding,
        retry: (() -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener {
                retry?.invoke()
            }
        }

        fun bind(loadState: LoadState) = with(binding) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }

    }
}