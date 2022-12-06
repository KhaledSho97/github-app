package com.khaledsh.core.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseItemViewHolder<in Data>(
    itemView: View,
    private val mItemClickListener: BaseRecyclerViewAdapter.OnItemClickListener?,
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    abstract fun bind(data: Data, position: Int)

    override fun onClick(view: View) {
        mItemClickListener?.onItemClick(view, absoluteAdapterPosition)
    }

}