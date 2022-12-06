package com.khaledsh.data.paging.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.khaledsh.data.api.services.GithubServices
import com.khaledsh.data.db.AppDatabase
import com.khaledsh.data.db.entity.RemoteKey
import com.khaledsh.data.db.entity.UserEntity
import com.khaledsh.data.mappers.UserEntityMapper
import com.khaledsh.domain.models.UserType
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception


private const val INITIAL_PAGE = 1
private const val PAGE_SIZE = 10

@OptIn(ExperimentalPagingApi::class)
class UsersRemoteMediator(
    private val parentUsername: String,
    private val database: AppDatabase,
    private val githubServices: GithubServices,
    private val userEntityMapper: UserEntityMapper,
    private val userType: UserType
) : RemoteMediator<Int, UserEntity>() {

    private val remoteKeyDao = database.getRemoteKeyDao()
    private val userDao = database.getUserDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> INITIAL_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.remoteKeyByUserType(parentUsername, userType)
                    }

                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey
                }
            }

            val response = when (userType) {
                UserType.FOLLOWER -> githubServices.getFollowers(
                    username = parentUsername, count = PAGE_SIZE, page = loadKey
                )
                UserType.FOLLOWING -> githubServices.getFollowing(
                    username = parentUsername, count = PAGE_SIZE, page = loadKey
                )
                else -> throw Exception("unexpected user type")
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    when (userType) {
                        UserType.FOLLOWER -> userDao.clearAllFollowers(parentUsername)
                        UserType.FOLLOWING -> userDao.clearAllFollowing(parentUsername)
                        else -> throw Exception("unexpected user type")
                    }
                }

                // Update RemoteKey
                remoteKeyDao.insertOrReplace(
                    RemoteKey(
                        userType = userType,
                        parentUsername = parentUsername,
                        nextKey = loadKey + 1
                    )
                )

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                userDao.insertAll(
                    userEntityMapper.mapList(
                        response,
                        userType,
                        parentUsername
                    )
                )
            }

            MediatorResult.Success(
                endOfPaginationReached = response.size < PAGE_SIZE
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}