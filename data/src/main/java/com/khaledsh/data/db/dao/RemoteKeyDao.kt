package com.khaledsh.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khaledsh.data.db.entity.RemoteKey
import com.khaledsh.domain.models.UserType

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE userType = :userType AND parentUsername = :parentUsername")
    suspend fun remoteKeyByUserType(
        parentUsername: String,
        userType: UserType
    ): RemoteKey

    @Query("DELETE FROM remote_keys WHERE parentUsername = :parentUsername AND userType = :userType")
    suspend fun deleteByUserType(parentUsername: String, userType: UserType)
}