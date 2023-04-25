package com.example.infinitscroll.screens.home

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.infinitscroll.presentation.topAppBarBackgroundColor
import com.example.infinitscroll.presentation.topAppBarContentColor

@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Home",
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
    )
}

@Composable
@Preview
fun HomeTopBarPreview() {
    HomeTopBar()
}