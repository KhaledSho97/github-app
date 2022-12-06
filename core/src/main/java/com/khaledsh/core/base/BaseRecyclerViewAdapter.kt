package com.khaledsh.core.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<Data, Holder : BaseItemViewHolder<Data>> :
    RecyclerView.Adapter<Holder>() {
    var data: ArrayList<Data> = ArrayList()
    var itemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(data[position], position)


    override fun getItemCount(): Int = data.size

    override fun getItemId(position: Int): Long = position.toLong()

    fun addData(dataList: List<Data>) {
        data.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addData(data: Data) {
        this.data.add(data)
        notifyDataSetChanged()
    }

    fun setData(dataList: List<Data>) {
        clearData()
        data.addAll(dataList)
        notifyDataSetChanged()
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

}