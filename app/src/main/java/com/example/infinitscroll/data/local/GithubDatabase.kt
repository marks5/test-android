package com.example.infinitscroll.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.infinitscroll.data.local.dao.GithubDao
import com.example.infinitscroll.data.local.dao.GithubRemoteKeysDao
import com.example.infinitscroll.domain.GithubRemoteKeys
import com.example.infinitscroll.domain.Repo

@Database(entities = [Repo::class, GithubRemoteKeys::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun repoDao(): GithubDao
    abstract fun remoteKeysDao(): GithubRemoteKeysDao
}