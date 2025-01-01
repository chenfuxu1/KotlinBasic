package com.chapter03.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("/repos/{owner}/{repo}")
    fun getRepository(@Path("owner") owner: String, @Path("repo") repo: String): Call<Repository>

    @GET("users/{login}")
    suspend fun getUserSuspend(@Path("login") login: String): User
}