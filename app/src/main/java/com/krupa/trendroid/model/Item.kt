package com.krupa.trendroid.model


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("forks_count")
    val forksCount: Long,

    @SerializedName("id")
    val id: Long,

    @SerializedName("language")
    val language: String?,

    @SerializedName("name")
    val name: String,

    @SerializedName("stargazers_count")
    val stargazersCount: Long,

    @SerializedName("owner")
    val owner: Owner,

    @SerializedName("description")
    val description: String?,

    @SerializedName("html_url")
    val htmlUrl: String,
)