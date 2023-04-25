package com.example.infinitscroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.example.infinitscroll.navigation.SetupNavGraph
import com.example.infinitscroll.presentation.InfinityScrollTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@ExperimentalCoilApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InfinityScrollTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }


}