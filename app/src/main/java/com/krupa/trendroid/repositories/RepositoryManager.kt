package com.krupa.trendroid.repositories

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.work.*
import com.krupa.trendroid.model.Item
import com.krupa.trendroid.repositories.database.LocalRepository
import com.krupa.trendroid.repositories.database.entities.ReadmeTable
import com.krupa.trendroid.repositories.network.APIRepository
import com.krupa.trendroid.services.RepoWorker
import com.krupa.trendroid.util.CommonMethods
import com.krupa.trendroid.util.Resource
import com.krupa.trendroid.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepositoryManager @Inject constructor(
    val context: Context,
    var apiRepository: APIRepository,
    var localRepository: LocalRepository) {

    private fun isConnectedToInternet(): Boolean {
        return CommonMethods.isConnectedToInternet(context)
    }

    fun setUpWorkRequest() {
        val myWorkBuilder = PeriodicWorkRequest.Builder(
            RepoWorker::class.java, 15, TimeUnit.MINUTES
        )
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val myWork = myWorkBuilder.setConstraints(constraint).build()
        val mWorkManager = WorkManager.getInstance(context)
        mWorkManager.enqueueUniquePeriodicWork(
            "fetch_repo",
            ExistingPeriodicWorkPolicy.KEEP, myWork
        )
    }

    @Throws(Exception::class)
    suspend fun getTrendingRepositories(): Resource<List<Item>> {
        val trendingRepoFromDB = localRepository.getAllRepositories()

        // Does db has data? -> YES -> Load and send data
        return if (trendingRepoFromDB.isNotEmpty()) {
            Resource.success(trendingRepoFromDB)
        } else {
            CoroutineScope(Dispatchers.IO).async {
                getTrendingRepoFromNetwork()
            }.await()
        }
    }

    @Throws(Exception::class)
    suspend fun getTrendingRepoFromNetwork(): Resource<List<Item>> {
        // Is connected to internet? -> YES -> load data, save in db and send
        if (isConnectedToInternet()) {
            var repos = apiRepository.getTrendingRepositories()
            localRepository.insertRepositoriesInDB(repos.items)

            try {
                for (item in repos.items) {
                    var readMeData =
                        apiRepository.getReadmeOfRepository(owner = item.owner.login ?: "", repo = item.name,authorization = Util.authKey)
                    localRepository.insertReadmeContent(
                        ReadmeTable(
                            repoId = item.id,
                            content = readMeData.content
                        )
                    )
                }
            }
            catch (e:Exception){
                e.printStackTrace()
                return Resource.success(repos.items)
            }
            return Resource.success(repos.items)
        } else {
            // No internet connection -> Send error
            return Resource.error("Please connect to internet and try again.", null)
        }
    }


    @Throws(Exception::class)
    suspend fun getRepositoryReadme(repoId: Long, owner: String, repo: String): Resource<String> {
        val data = localRepository.getReadmeContentByRepoID(repoId)

        return if (data != null) {
            // DB has data -> Load and send data
            val decoded = String(Base64.decode(data?.content, Base64.DEFAULT))
            Resource.success(decoded)
        } else {
            // DB not having data so load data from internet and send
            if (isConnectedToInternet()) {
                var readMeData = apiRepository.getReadmeOfRepository(owner, repo,Util.authKey)
                localRepository.insertReadmeContent(
                    ReadmeTable(
                        repoId = repoId,
                        content = readMeData.content
                    )
                )
                val decoded = String(Base64.decode(readMeData.content, Base64.DEFAULT))
                Resource.success(decoded)
            } else {
                // DB not having data + no internet -> Send error
                Resource.error(
                    "Please connect to internet and try again.",
                    null
                )
            }
        }
    }
}