package com.khaledsh.data.repositories

import androidx.paging.*
import com.khaledsh.data.api.services.GithubServices
import com.khaledsh.data.api.util.ApiHelper
import com.khaledsh.data.db.AppDatabase
import com.khaledsh.data.mappers.UserEntityMapper
import com.khaledsh.data.paging.mediator.UsersRemoteMediator
import com.khaledsh.data.paging.sources.UserPagingSource
import com.khaledsh.domain.models.Result
import com.khaledsh.domain.models.User
import com.khaledsh.domain.models.UserType
import com.khaledsh.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserRepositoryImpl @Inject constructor(
    private val githubServices: GithubServices,
    private val apiHelper: ApiHelper,
    private val appDatabase: AppDatabase,
    private val userEntityMapper: UserEntityMapper,
) : UserRepository {

    private val userDao = appDatabase.getUserDao()

    override suspend fun getUserFromLocalDb(username: String): User? =
        userDao.findUserByUsername(username)?.toUser()


    override suspend fun getUser(username: String): Result<User> {
        return apiHelper.safeExecute(block = {
            githubServices.getUser(username)
        }, transform = {
            val entity = userEntityMapper.map(it, UserType.NORMAL, null)
            userDao.insert(entity)
            entity.toUser()
        })
    }

    override suspend fun searchUsers(query: String): Flow<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { UserPagingSource(query, githubServices, userEntityMapper) }
    ).flow.map { pagingData ->
        pagingData.map { userEntity -> userEntity.toUser() }
    }

    override suspend fun getFollowers(username: String): Flow<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { userDao.followersPagingSource(username) },
        remoteMediator = UsersRemoteMediator(
            username,
            appDatabase,
            githubServices,
            userEntityMapper,
            UserType.FOLLOWER
        )
    ).flow.map { pagingData ->
        pagingData.map { userEntity -> userEntity.toUser() }
    }

    override suspend fun getFollowing(username: String): Flow<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { userDao.followingPagingSource(username) },
        remoteMediator = UsersRemoteMediator(
            username,
            appDatabase,
            githubServices,
            userEntityMapper,
            UserType.FOLLOWING
        )
    ).flow.map { pagingData ->
        pagingData.map { userEntity -> userEntity.toUser() }
    }
}

