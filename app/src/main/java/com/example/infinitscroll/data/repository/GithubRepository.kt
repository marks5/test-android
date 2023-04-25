package com.example.infinitscroll.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.infinitscroll.data.local.GithubDatabase
import com.example.infinitscroll.data.paging.GithubRemoteMediator
import com.example.infinitscroll.data.remote.GithubAPI
import com.example.infinitscroll.domain.Repo
import com.example.infinitscroll.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class GithubRepository @Inject constructor(
    private val githubAPI: GithubAPI,
    private val githubDatabase: GithubDatabase
) {

    fun getAllPages(): Flow<PagingData<Repo>> {
        val pagingSourceFactory = { githubDatabase.repoDao().getGithub()}
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = GithubRemoteMediator(
                githubAPI = githubAPI,
                githubDatabase = githubDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}