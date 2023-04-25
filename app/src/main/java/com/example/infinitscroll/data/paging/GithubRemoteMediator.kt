package com.example.infinitscroll.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.infinitscroll.data.local.GithubDatabase
import com.example.infinitscroll.data.remote.GithubAPI
import com.example.infinitscroll.domain.GithubRemoteKeys
import com.example.infinitscroll.domain.Repo
import com.example.infinitscroll.util.Constants.ITEMS_PER_PAGE
import com.example.infinitscroll.util.Constants.QUERY_PAGE
import javax.inject.Inject

@ExperimentalPagingApi
class GithubRemoteMediator @Inject constructor(
    private val githubAPI: GithubAPI,
    private val githubDatabase: GithubDatabase
): RemoteMediator<Int, Repo>() {

    private val githubDao = githubDatabase.repoDao()
    private val githubRemoteKeysDao = githubDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Repo>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = githubAPI.searchRepos(page = currentPage,
                itemsPerPage = ITEMS_PER_PAGE,
                query = QUERY_PAGE)
            val endOfPaginationReached = response.items.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            githubDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    githubDao.deleteAllPages()
                    githubRemoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = response.items.map { each ->
                    GithubRemoteKeys(
                        id = each.id.toString(),
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                githubRemoteKeysDao.addAllRemoteKeys(remotekeys = keys)
                githubDao.addPages(pages = response.items)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Repo>
    ): GithubRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                githubRemoteKeysDao.getRemoteKeys(id = id.toString())
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Repo>
    ): GithubRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { each ->
                githubRemoteKeysDao.getRemoteKeys(id = each.id.toString())
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Repo>
    ): GithubRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { each ->
                githubRemoteKeysDao.getRemoteKeys(id = each.id.toString())
            }
    }
}