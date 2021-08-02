package com.krupa.trendroid.repositories.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RepositoryTable::class,
        parentColumns = arrayOf("repo_id"),
        childColumns = arrayOf("repo_id"),
        onDelete = CASCADE
    )],
    tableName = "repository_readme"
)

data class ReadmeTable(
    @PrimaryKey(autoGenerate = true) val readme_id: Long = 0,
    @ColumnInfo(name = "repo_id") val repoId: Long,
    @ColumnInfo(name = "content") val content: String?
)
