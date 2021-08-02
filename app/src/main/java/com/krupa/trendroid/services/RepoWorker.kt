package com.krupa.trendroid.services

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.krupa.trendroid.MyApplication
import com.krupa.trendroid.repositories.database.LocalRepository
import com.krupa.trendroid.repositories.database.entities.ReadmeTable
import com.krupa.trendroid.repositories.network.APIRepository
import okhttp3.internal.Util
import javax.inject.Inject

class RepoWorker (var context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    @Inject
    lateinit var apiRepository: APIRepository

    @Inject
    lateinit var localRepository: LocalRepository

    init {
        (context as MyApplication).getComponent().inject(this)
    }

    override suspend fun doWork(): Result {
        fetchRepositoriesAndReadme()
        return Result.success()
    }

    private suspend fun fetchRepositoriesAndReadme(): Boolean {
        try {
            val repos = apiRepository.getTrendingRepositories()
            localRepository.insertRepositoriesInDB(repos.items)

            for (item in repos.items) {
                val readMeData =
                    apiRepository.getReadmeOfRepository(item.owner.login ?: "", item.name,com.krupa.trendroid.util.Util.authKey)

                localRepository.insertReadmeContent(
                    ReadmeTable(repoId = item.id, content = readMeData.content)
                )
            }

            return true;
        } catch (e: Exception) {
            e.printStackTrace()
            return false;
        }
    }
}