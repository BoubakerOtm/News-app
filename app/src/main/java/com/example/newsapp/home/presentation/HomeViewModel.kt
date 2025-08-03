package com.example.newsapp.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.app.network.Resource
import com.example.newsapp.home.data.Article
import com.example.newsapp.home.data.NewsResponse
import com.example.newsapp.home.domain.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val newsResponse: NewsResponse? = null,
    val error: String? = null,
)

sealed class UiEvent {
    data object Idle : UiEvent()
    data class UiArticleClick(val article: Article) : UiEvent()
    data object UiBackClick : UiEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.onStart { initUi() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState(isLoading = true),
    )
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

            is UiEvent.UiBackClick -> {
            }

            else -> Unit
        }

    }

    fun initUi() {
        viewModelScope.launch {
            homeRepository.getTopHeadlines().collectLatest { resource ->
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

        }
    }
}