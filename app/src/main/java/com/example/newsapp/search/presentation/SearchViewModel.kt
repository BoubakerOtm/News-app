package com.example.newsapp.search.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.home.data.Article
import com.example.newsapp.search.domain.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class SearchState(
    val query: String = "",
    val results: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

sealed class UiEvent {
    data object Idle : UiEvent()
    data class OnQueryValue(val query: String) : UiEvent()
    data object Search : UiEvent()
    data class Select(val article: Article) : UiEvent()
    data object Clear : UiEvent()
    data object Back : UiEvent()
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val state = _uiState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            SearchState(),
        )
    private val _event = Channel<UiEvent>()
    val event = _event.receiveAsFlow()

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.OnQueryValue -> {
                _uiState.update {
                    it.copy(
                        query = event.query,
                    )
                }
            }

            is UiEvent.Search -> {
                viewModelScope.launch {
                    try {
                        val response = searchRepository.searchNews(_uiState.value.query)
                        _uiState.update {
                            it.copy(
                                results = response.articles,
                                isLoading = false,
                            )
                        }
                    } catch (e: Exception) {
                        _uiState.update {
                            it.copy(
                                error = e.message,
                                isLoading = false,
                            )
                        }

                    }

                }
            }

            is UiEvent.Select -> {
                viewModelScope.launch {
                    _event.send(UiEvent.Select(event.article))
                }
            }

            is UiEvent.Clear -> {
                _uiState.update {
                    it.copy(
                        query = "",
                    )
                }
            }

            is UiEvent.Back -> {
                viewModelScope.launch {
                    _event.send(UiEvent.Back)
                }
            }

            else -> {}
        }

    }


}