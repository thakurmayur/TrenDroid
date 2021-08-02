package com.krupa.trendroid.model


import com.google.gson.annotations.SerializedName

data class ReadMeResponse(
    @SerializedName("content")
    val content: String,
)