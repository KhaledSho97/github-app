package com.khaledsh.data.models

import com.google.gson.annotations.SerializedName

data class UserDTO(
    val id: Long,
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    val name: String?,
    val bio: String?,
    @SerializedName("public_repos") val repos: Int?,
    val followers: Int?,
    val following: Int?
)