package com.khaledsh.data.paging.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.khaledsh.data.api.services.GithubServices
import com.khaledsh.data.db.entity.UserEntity
import com.khaledsh.data.mappers.UserEntityMapper
import com.khaledsh.domain.models.UserType
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

private const val INITIAL_PAGE = 1
private const val PAGE_SIZE = 10

class UserPagingSource(
    private val query: String,
    private val githubServices: GithubServices,
    private val userEntityMapper: UserEntityMapper
) : PagingSource<Int, UserEntity>() {

    /**
     * calls api if there is any error getting results then return the [LoadResult.Error]
     * for successful response return the results using [LoadResult.Page] for some reason if the results
     * are empty from service like in case of no more data from api then we can pass [null] to
     * send signal that source has reached the end of list
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserEntity> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: INITIAL_PAGE
        return try {
            val response = githubServices.searchUsers(query, PAGE_SIZE, page)
            LoadResult.Page(
                userEntityMapper.mapList(response.items, UserType.NORMAL, null),
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if (response.items.size < PAGE_SIZE) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserEntity>): Int? = state.anchorPosition
}