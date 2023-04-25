package com.example.infinitscroll.navigation

sealed class Screen (val route: String) {
    object Home: Screen("home_screen")
}