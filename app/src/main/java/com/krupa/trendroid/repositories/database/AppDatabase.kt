package com.krupa.trendroid.repositories.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.krupa.trendroid.repositories.database.dao.RepositoryDao
import com.krupa.trendroid.repositories.database.entities.ReadmeTable
import com.krupa.trendroid.repositories.database.entities.RepositoryTable

@Database(entities = [RepositoryTable::class, ReadmeTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "trending_repo.db")
            .build()
    }
}