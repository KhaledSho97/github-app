package com.khaledsh.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khaledsh.domain.models.UserType

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0,
    val userType: UserType,
    val parentUsername: String,
    val nextKey: Int?
)