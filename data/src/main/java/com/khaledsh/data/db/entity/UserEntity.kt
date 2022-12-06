package com.khaledsh.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khaledsh.domain.models.User
import com.khaledsh.domain.models.UserType

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val dbId: Long,
    val id: Long,
    val userType: UserType,
    val username: String,
    val name: String?,
    val avatarUrl: String,
    val bio: String?,
    val repos: Int?,
    val followers: Int?,
    val following: Int?,
    val parentUsername: String?
) {
    fun toUser(): User =
        User(id, userType, username, name, avatarUrl, bio, repos, followers, following)
}