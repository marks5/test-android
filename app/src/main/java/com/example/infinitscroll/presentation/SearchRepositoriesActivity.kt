package com.example.infinitscroll.presentation

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.infinitscroll.Injection
import com.example.infinitscroll.databinding.ActivitySearchRepositoriesBinding
import com.example.infinitscroll.domain.Repo
import com.example.infinitscroll.domain.RepoSearchResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchRepositoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySearchRepositoriesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(owner = this))
            .get(SearchRepositoriesViewModel::class.java)

        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        binding.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )
    }

    private fun ActivitySearchRepositoriesBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<Repo>>,
        uiActions: (UiAction) -> Unit
    ) {
        val repoAdapter = ReposAdapter()
        list.adapter = repoAdapter

        bindList(
            repoAdapter = repoAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions,
        )
    }

    private fun ActivitySearchRepositoriesBinding.bindList(
        repoAdapter: ReposAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<Repo>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
        retryButton.setOnClickListener { repoAdapter.retry() }

        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })

        lifecycleScope.launch {
            pagingData.collectLatest { repoAdapter.submitData(it) }
        }

        lifecycleScope.launch {
            repoAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && repoAdapter.itemCount == 0
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                list.isVisible = !isListEmpty
                emptyList.isVisible = isListEmpty
                retryButton.isVisible = loadState.source.refresh is LoadState.Error

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        this@SearchRepositoriesActivity,
                        "\uD83D\uDE28 Bad ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}
