package com.khaledsh.features.profile.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.khaledsh.domain.models.User
import com.khaledsh.features.profile.databinding.ViewholderListItemBinding
import javax.inject.Inject

abstract class ListAdapter : PagingDataAdapter<User, ListViewHolder>(COMPARATOR) {

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

class ListAdapterImpl @Inject constructor() : ListAdapter() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ViewholderListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}

class ListViewHolder(
    private val binding: ViewholderListItemBinding,
    private val clickListener: ((user: User) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: User) {
        binding.user = data
        binding.root.setOnClickListener {
            clickListener?.invoke(data)
        }
    }
}