package com.khaledsh.domain.models

data class User(
    val id: Long,
    val type: UserType,
    val username: String,
    val name: String?,
    val avatarUrl: String,
    val bio: String?,
    val repos: Int?,
    val followers: Int?,
    val following: Int?
)

enum class UserType {
    NORMAL, FOLLOWER, FOLLOWING
}