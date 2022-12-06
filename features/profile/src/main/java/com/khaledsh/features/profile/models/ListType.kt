package com.khaledsh.features.profile.models

import androidx.annotation.StringRes
import com.khaledsh.features.profile.R

enum class ListType(@StringRes val title: Int) {
    FOLLOWERS(R.string.followers),
    FOLLOWING(R.string.following)
}