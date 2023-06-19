package com.blihm.balihometest.data.network.api

import com.blihm.balihometest.data.network.model.UserDto
import com.blihm.balihometest.data.network.model.UserRepositoryDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<UserDto>

    @GET("users/{login}/repos")
    suspend fun getUserRepositories(
        @Path("login") login: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): List<UserRepositoryDto>
}