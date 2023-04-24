package com.example.infinitscroll.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.infinitscroll.domain.GithubRemoteKeys

@Dao
interface GithubRemoteKeysDao {
    @Query("SELECT * FROM github_remote_keys_table WHERE id =:id")
    fun getRemoteKeys(id: String): GithubRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remotekeys: List<GithubRemoteKeys>)

    @Query("DELETE FROM github_remote_keys_table")
    suspend fun deleteAllRemoteKeys()
}