package com.example.infinitscroll.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.infinitscroll.domain.Repo

@Dao
interface GithubDao {

    @Query("SELECT * FROM github_table")
    fun getGithub(): PagingSource<Int, Repo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPages(pages: List<Repo>)

    @Query("DELETE FROM github_table")
    suspend fun deleteAllPages()
}