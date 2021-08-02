package com.krupa.trendroid.repositories.network

import com.krupa.trendroid.model.ReadMeResponse
import com.krupa.trendroid.model.TrendingRepoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface APIRepository {
    @GET("search/repositories?q=android&sort=stars&order=desc&per_page=10")
    suspend fun getTrendingRepositories(): TrendingRepoResponse

    @GET("repos/{owner}/{repo}/readme")
    suspend fun getReadmeOfRepository(@Path("owner") owner: String,@Path("repo") repo: String, @Header("Authorization") authorization:String): ReadMeResponse
}