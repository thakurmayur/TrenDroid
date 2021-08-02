package com.krupa.trendroid.repositories

import com.krupa.trendroid.repositories.network.APIRepository
import com.krupa.trendroid.util.Util
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RepositoryManagerTest {
    lateinit var apiRepository: APIRepository

    @Before
    internal fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        apiRepository = retrofit.create(APIRepository::class.java)
    }

    @Test
    fun check_repository_count_is_10() {
        runBlocking {
            val repo = apiRepository.getTrendingRepositories()
            assertEquals(10, repo.items.size)
        }
    }

    @Test
    fun check_content_is_not_null() {
        runBlocking {
            val content = apiRepository.getReadmeOfRepository("flutter","flutter", Util.authKey)
            assertNotNull(content.content)
        }
    }
}