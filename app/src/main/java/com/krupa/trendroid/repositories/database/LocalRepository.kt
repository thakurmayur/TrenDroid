package com.krupa.trendroid.repositories.database

import com.krupa.trendroid.model.Item
import com.krupa.trendroid.model.Owner
import com.krupa.trendroid.repositories.database.dao.RepositoryDao
import com.krupa.trendroid.repositories.database.entities.ReadmeTable
import com.krupa.trendroid.repositories.database.entities.RepositoryTable
import javax.inject.Inject

class LocalRepository @Inject constructor(val repositoryDao: RepositoryDao) {

    suspend fun insertRepositoriesInDB(list: List<Item>) {
        repositoryDao.deleteAllRepositories()

        for (item in list) {
            val rt = RepositoryTable(
                repoId = item.id,
                avatar = item.owner.avatarUrl,
                name = item.name,
                description = item.description,
                language = item.language,
                login = item.owner.login,
                stars = item.stargazersCount,
                forks = item.forksCount,
                html_url = item.htmlUrl
            )
            repositoryDao.insertRepositories(rt)
        }
    }

    suspend fun getAllRepositories(): List<Item> {
        var list = arrayListOf<Item>()
        val dbList = repositoryDao.getAllRepo()
        for (rp in dbList) {
            list.add(
                Item(
                    id = rp.repoId,
                    name = rp.name,
                    description = rp.description,
                    language = rp.language,
                    forksCount = rp.forks,
                    stargazersCount = rp.stars, owner = Owner(rp.avatar, rp.login),
                    htmlUrl = rp.html_url
                )
            )
        }
        return list
    }

    suspend fun getReadmeContentByRepoID(repoID: Long): ReadmeTable? {
        return repositoryDao.getReadmeContent(repoID)
    }

    suspend fun insertReadmeContent(data: ReadmeTable) {
        repositoryDao.insertIntoReadMe(data)
    }
}