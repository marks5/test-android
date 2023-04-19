package com.example.infinitscroll

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import com.example.infinitscroll.data.GithubRepository
import com.example.infinitscroll.domain.Repo
import com.example.infinitscroll.presentation.SearchRepositoriesViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.flow
import org.junit.Test

class SearchRepositoriesViewModelTest : BaseTest() {

    private lateinit var viewModel: SearchRepositoriesViewModel
    private val service = mockk<GithubRepository>()
    private val stateHandle = SavedStateHandle()

    override fun setup() {
        super.setup()
        stateHandle.set("LAST_QUERY_SCROLLED", "language:kotlin")
        viewModel = SearchRepositoriesViewModel(service, stateHandle)
    }

    @Test
    fun onSuccess() = viewModel.run {
        val eventList = listOf(mockk<Repo>())
        val query = "language:kotlin"
        coEvery { service.getSearchResultStream(query) } returns flow {
            emit(PagingData.from(eventList))
        }

        coVerify {
            service.getSearchResultStream(query)
        }

        assertNotNull(viewModel.state.value)
    }

}