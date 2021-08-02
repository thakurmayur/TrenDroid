package com.krupa.trendroid.di.modules

import android.app.Application
import com.krupa.trendroid.repositories.RepositoryManager
import com.krupa.trendroid.repositories.database.LocalRepository
import com.krupa.trendroid.repositories.network.APIRepository
import com.krupa.trendroid.ui.home.TrendingRepoAdapter
import dagger.Module
import dagger.Provides

@Module(includes = [RetroModule::class, RoomModule::class, ApplicationModule::class])
class MainModule {

    @Provides
    fun getTrendingRepoAdapter(): TrendingRepoAdapter {
        return TrendingRepoAdapter()
    }

    @Provides
    fun getRepositoryManager(
        application: Application,
        apiRepository: APIRepository,
        localRepository: LocalRepository
    ): RepositoryManager {
        return RepositoryManager(application, apiRepository, localRepository)
    }
}