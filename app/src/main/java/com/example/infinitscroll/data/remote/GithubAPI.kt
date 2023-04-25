package com.example.infinitscroll.data.remote

import com.example.infinitscroll.domain.Root
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {

    @GET("search/repositories?sort=stars&q=language:kotlin")
    suspend fun searchRepos(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Root
}
