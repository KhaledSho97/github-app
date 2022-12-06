package com.khaledsh.core.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:loadFromUrl")
fun ImageView.setImageFromUrl(url: String?) {
    url?.let {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
}