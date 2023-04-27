package com.example.infinitscroll
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.annotation.ExperimentalCoilApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalPagingApi
@ExperimentalCoilApi
class MainActivityEspressoTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun checkIfLanguageIsKotlin() {
        composeRule
            .onAllNodes(hasText("Language: Kotlin"))
    }

}