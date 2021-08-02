package com.krupa.trendroid.di.modules

import android.app.Application
import com.krupa.trendroid.repositories.database.AppDatabase
import com.krupa.trendroid.repositories.database.LocalRepository
import com.krupa.trendroid.repositories.database.dao.RepositoryDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(val application: Application) {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return AppDatabase.invoke(application)
    }

    @Provides
    @Singleton
    fun provideRepositoryDao(appDatabase: AppDatabase): RepositoryDao {
        return appDatabase.repositoryDao()
    }

    @Provides
    @Singleton
    fun getLocalRepository(repositoryDao: RepositoryDao): LocalRepository {
        return LocalRepository(repositoryDao)
    }
}