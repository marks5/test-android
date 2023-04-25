package com.example.infinitscroll.domain

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Root(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<Repo> = emptyList(),
    val nextPage: Int? = null
)




