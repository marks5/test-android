package com.example.infinitscroll.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.infinitscroll.screens.common.ListContent

@ExperimentalCoilApi
@ExperimentalPagingApi
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val getAllPages = homeViewModel.getPages.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            HomeTopBar()
        },
        content = {
            ListContent(items = getAllPages)
        }
    )
}