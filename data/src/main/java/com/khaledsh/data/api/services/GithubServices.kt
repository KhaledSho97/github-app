package com.khaledsh.data.api.services

import com.khaledsh.data.models.PaginatedApiResponse
import com.khaledsh.data.models.UserDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubServices {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("per_page") count: Int,
        @Query("page") page: Int
    ): PaginatedApiResponse<UserDTO>


    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<UserDTO>

    @GET("/users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Query("per_page") count: Int,
        @Query("page") page: Int
    ): List<UserDTO>

    @GET("/users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String,
        @Query("per_page") count: Int,
        @Query("page") page: Int
    ): List<UserDTO>
}