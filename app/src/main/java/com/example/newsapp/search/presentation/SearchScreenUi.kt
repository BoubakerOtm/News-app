package com.example.newsapp.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import com.example.newsapp.home.data.Article
import com.example.newsapp.home.data.CardNews
import com.example.newsapp.home.presentation.ArticleRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenUi(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    navigateToDetails: (article: CardNews) -> Boolean,
    navigateToMain: () -> NavKey?,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.event.collectAsStateWithLifecycle(initialValue = UiEvent.Idle)

    LaunchedEffect(event) {
        when (event) {
            is UiEvent.Select -> {
//                navigateToDetails(
////                    (event as UiEvent.Select).article,
//                )
            }

            is UiEvent.Back -> {
                navigateToMain()
            }

            else -> Unit
        }
    }

    Column(
        modifier = modifier,
    ) {
        SearchBar(
            query = state.query,
            onQueryChange = { viewModel.onEvent(UiEvent.OnQueryValue(it)) },
            onSearch = { viewModel.onEvent(UiEvent.Search) },
            active = false,
            onActiveChange = {},
            placeholder = { Text("Search") },
            leadingIcon = {
                IconButton(onClick = { viewModel.onEvent(UiEvent.Back) }) {
//                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            trailingIcon = {
                IconButton(onClick = { viewModel.onEvent(UiEvent.Clear) }) {
//                    Icon(Icons.Filled.Clear, contentDescription = null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            content = {}
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val articles = state.results
            items(
                items = articles,
                key = { article -> article.title },
            ) { article ->
                ArticleRow(
                    article = article,
                    onArticleClick = { viewModel.onEvent(UiEvent.Select(article)) },
                )
                if (articles.indexOf(article) < articles.lastIndex) {
                    Divider(color = Color.LightGray, thickness = 1.dp)
                }
            }
        }

    }
}