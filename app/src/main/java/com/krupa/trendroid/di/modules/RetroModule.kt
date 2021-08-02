package com.krupa.trendroid.di.modules

import com.krupa.trendroid.repositories.network.APIRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetroModule {
    private val BASE_URL = "https://api.github.com/";

    @Singleton
    @Provides
    fun getRetrofitServiceInterface(retrofit: Retrofit): APIRepository {
        return retrofit.create(APIRepository::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}