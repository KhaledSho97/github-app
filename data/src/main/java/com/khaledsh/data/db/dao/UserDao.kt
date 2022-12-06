package com.khaledsh.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khaledsh.data.db.entity.UserEntity
import com.khaledsh.domain.models.UserType

@Dao
interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)


    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    fun findUserByUsername(username: String): UserEntity?


    @Query("DELETE FROM users WHERE id = :id")
    fun deleteUserById(id: Long)


    @Query("SELECT * FROM users WHERE userType = :userType AND parentUsername = :parentUsername")
    fun followersPagingSource(
        parentUsername: String,
        userType: UserType = UserType.FOLLOWER
    ): PagingSource<Int, UserEntity>


    @Query("SELECT * FROM users WHERE userType = :userType AND parentUsername = :parentUsername")
    fun followingPagingSource(
        parentUsername: String,
        userType: UserType = UserType.FOLLOWING
    ): PagingSource<Int, UserEntity>


    @Query("DELETE FROM users WHERE userType = :userType AND parentUsername = :parentUsername")
    fun clearAllFollowers(parentUsername: String, userType: UserType = UserType.FOLLOWER)


    @Query("DELETE FROM users WHERE userType = :userType AND parentUsername = :parentUsername")
    fun clearAllFollowing(parentUsername: String, userType: UserType = UserType.FOLLOWING)
}