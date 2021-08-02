package com.krupa.trendroid.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krupa.trendroid.model.Item
import com.krupa.trendroid.repositories.RepositoryManager
import com.krupa.trendroid.util.Resource
import javax.inject.Inject


class RepositoryViewModel @Inject constructor(
    var repositoryManager: RepositoryManager) : ViewModel() {

    var repositoriesList = MutableLiveData<Resource<List<Item>>>();
    var repoReadmeData = MutableLiveData<Resource<String>>();


    suspend fun getTrendingRepositories() {
        try {
            repositoriesList.postValue(Resource.loading(null))
            val repos = repositoryManager.getTrendingRepositories()
            repositoriesList.postValue(repos)
        } catch (e: Exception) {
            e.printStackTrace()
            repositoriesList.postValue(
                Resource.error(
                    "Something went wrong. Please try again later.",
                    null
                )
            )
        }
    }

    suspend fun getTrendingRepoFromInternet() {
        try {
            repositoriesList.postValue(Resource.loading(null))
            val repos = repositoryManager.getTrendingRepoFromNetwork()
            repositoriesList.postValue(repos)
        } catch (e: Exception) {
            e.printStackTrace()
            repositoriesList.postValue(
                Resource.error(
                    "Something went wrong. Please try again later.",
                    null
                )
            )
        }
    }

    suspend fun getRepositoryReadme(repoId: Long, owner: String, repo: String) {
        try {
            repoReadmeData.postValue(Resource.loading(null))
            val repos = repositoryManager.getRepositoryReadme(repoId, owner, repo)
            repoReadmeData.postValue(repos)
        } catch (e: Exception) {
            e.printStackTrace()
            repoReadmeData.postValue(
                Resource.error(
                    "Something went wrong. Please try again later.",
                    null
                )
            )
        }
    }

    fun setUpWorkRequest() {
        repositoryManager.setUpWorkRequest()
    }
}