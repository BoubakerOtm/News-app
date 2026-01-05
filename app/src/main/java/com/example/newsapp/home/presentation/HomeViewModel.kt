package com.example.newsapp.home.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.app.Constants.GENERAL
import com.example.newsapp.app.Constants.TECHNOLOGY
import com.example.newsapp.app.network.Resource
import com.example.newsapp.home.data.Article
import com.example.newsapp.home.data.CategoryItem
import com.example.newsapp.home.data.HomeRepository
import com.example.newsapp.home.data.NewsResponse
import com.example.newsapp.home.data.getItems
import com.example.newsapp.home.domain.HomeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class HomeUiState(
    val isLoading: Boolean = false,
    val newsResponse: NewsResponse? = null,
    val error: String? = null,
    val selectedCategory: CategoryItem? = getItems.first(),
)

sealed class UiEvent {
    data class UiArticleClick(val article: Article) : UiEvent()
    data class CategorySelected(val category: CategoryItem?) : UiEvent()
    data object UiBackClick : UiEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val headlines = _uiState
        .map { it.selectedCategory?.value ?: GENERAL }
        .distinctUntilChanged()
        .flatMapLatest { category ->
            homeRepository
                .getTopHeadlines(category)
                .cachedIn(viewModelScope)
        }


    init {
        initUi()
    }

    private val _event: Channel<UiEvent> = Channel()
    val event = _event.receiveAsFlow()

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.UiArticleClick -> {
                viewModelScope.launch {
                    _event.send(
                        UiEvent.UiArticleClick(event.article),
                    )
                }
            }

            is UiEvent.CategorySelected -> {
                _uiState.update {
                    it.copy(
                        selectedCategory = event.category
                    )
                }
            }

            is UiEvent.UiBackClick -> {
            }

        }

    }

    fun initUi() {
        /*viewModelScope.launch {
            homeRepositoryImpl.getTopHeadlines().collectLatest { resource ->
                when (resource) {
                    is Resource.Success<*> -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = null,
                                newsResponse = resource.data as NewsResponse?,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = resource.exception.message,
                                newsResponse = null,
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = null,
                                newsResponse = null,
                            )
                        }
                    }
                }


            }

        }*/
    }
}