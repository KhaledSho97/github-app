package com.khaledsh.features.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.khaledsh.domain.models.User
import com.khaledsh.features.search.databinding.ViewholderSearchResultBinding
import javax.inject.Inject

abstract class SearchResultAdapter : PagingDataAdapter<User, SearchResultViewHolder>(COMPARATOR) {

    var clickListener: ((user: User) -> Unit)? = null

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }

}

class SearchResultAdapterImpl @Inject constructor() : SearchResultAdapter() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding = ViewholderSearchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchResultViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}

class SearchResultViewHolder(
    private val binding: ViewholderSearchResultBinding,
    private val clickListener: ((user: User) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: User) {
        binding.user = data
        binding.root.setOnClickListener {
            clickListener?.invoke(data)
        }
    }
}