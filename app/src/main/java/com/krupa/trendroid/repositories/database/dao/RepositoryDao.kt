package com.krupa.trendroid.repositories.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krupa.trendroid.repositories.database.entities.ReadmeTable
import com.krupa.trendroid.repositories.database.entities.RepositoryTable

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM trending_repositories ORDER BY stars DESC")
    suspend fun getAllRepo(): List<RepositoryTable>

    @Query("SELECT * FROM trending_repositories WHERE repo_id = :id")
    suspend fun findDetailsByRepoID(id: Long): RepositoryTable

    @Query("DELETE FROM trending_repositories")
    suspend fun deleteAllRepositories()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(repo: RepositoryTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoReadMe(data: ReadmeTable)

    @Query("SELECT * FROM repository_readme WHERE repo_id = :repoId")
    suspend fun getReadmeContent(repoId: Long): ReadmeTable
}