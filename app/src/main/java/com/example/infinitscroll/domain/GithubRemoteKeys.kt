package com.example.infinitscroll.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.infinitscroll.util.Constants.GITHUB_REMOTE_KEYS_TABLE

@Entity(tableName = GITHUB_REMOTE_KEYS_TABLE)
data class GithubRemoteKeys (
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val prevPage: Int?,
    val nextPage: Int?
)