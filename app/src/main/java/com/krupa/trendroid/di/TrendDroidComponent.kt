package com.krupa.trendroid.di

import com.krupa.trendroid.di.modules.*
import com.krupa.trendroid.services.RepoWorker
import com.krupa.trendroid.ui.home.TrendingRepoActivity
import com.krupa.trendroid.ui.readme.RepositoryReadmeActivity
import com.krupa.trendroid.viewmodels.RepositoryViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainModule::class,
    RetroModule::class, RoomModule::class,
    ApplicationModule::class,ViewModelModule::class])
interface TrendDroidComponent {
    fun inject(trendingRepoActivity: TrendingRepoActivity)
    fun inject(repositoryReadmeActivity: RepositoryReadmeActivity)
    fun inject(repoWorker: RepoWorker)
}