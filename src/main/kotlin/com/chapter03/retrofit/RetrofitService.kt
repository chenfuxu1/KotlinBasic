package com.chapter03.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-03 21:49
 *
 * Desc:
 */
object RetrofitService {
    val getRetrofit: GitHubApi =
        Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build()
            .create(GitHubApi::class.java)
}