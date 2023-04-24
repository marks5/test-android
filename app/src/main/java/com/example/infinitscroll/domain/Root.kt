package com.example.infinitscroll.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Root(
    @SerialName("total_count") val total: Int = 0,
    @SerialName("items") val items: List<Repo> = emptyList(),
    val nextPage: Int? = null
)




