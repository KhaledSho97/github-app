package com.khaledsh.domain.repositories

import androidx.paging.PagingData
import com.khaledsh.domain.models.Result
import com.khaledsh.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUserFromLocalDb(username: String): User?

    suspend fun getUser(username: String): Result<User>

    suspend fun searchUsers(query: String): Flow<PagingData<User>>

    suspend fun getFollowers(username: String): Flow<PagingData<User>>

    suspend fun getFollowing(username: String): Flow<PagingData<User>>

}