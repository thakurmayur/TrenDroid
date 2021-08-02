package com.krupa.trendroid.repositories.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending_repositories")
data class RepositoryTable(
    @ColumnInfo(name = "repo_id")
    @PrimaryKey(autoGenerate = false)
    val repoId: Long,

    @ColumnInfo(name = "avatar") val avatar: String?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "language") val language: String?,
    @ColumnInfo(name = "html_url") val html_url: String,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name = "stars") val stars: Long,
    @ColumnInfo(name = "forks") val forks: Long,
)
