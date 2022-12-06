package com.khaledsh.data.models

import com.google.gson.annotations.SerializedName

data class PaginatedApiResponse<T>(
    @SerializedName("total_count")
    val total: Int,
    val items: List<T>
)