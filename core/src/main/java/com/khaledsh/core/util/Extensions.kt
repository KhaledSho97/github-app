package com.khaledsh.core.util

import android.content.Context
import com.khaledsh.domain.models.Failure

fun Failure.getErrorMessage(context: Context): String {
    return when {
        message != null -> {
            message!!
        }
        messageRes != null -> {
            context.getString(this.messageRes!!)
        }
        else -> {
            "Unknown error has occurred"
        }
    }
}
