package com.khaledsh.core.util

import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.khaledsh.domain.models.Failure

@BindingAdapter("navigationOnClick")
fun Toolbar.setNavigationClickListener(listener: () -> Unit) {
    this.setNavigationOnClickListener {
        listener()
    }
}

@BindingAdapter("app:textFromFailure")
fun TextView.setTextFromFailure(failure: Failure?) {
    failure?.let {
        this.text = failure.getErrorMessage(context)
    }
}