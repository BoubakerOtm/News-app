package com.example.newsapp.home.presentation

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.newsapp.home.data.Article

@Composable
fun HomeScreenUi(
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    onButtonClick: (article: Article) -> Unit = {},
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        if (uiState.isLoading) {
            LoadingIndicator()
        } else if (uiState.error != null) {
            ErrorView(errorMessage = uiState.error!!)
        } else {
            val articles = uiState.newsResponse?.articles ?: emptyList()
            if (articles.isEmpty()) { // Handle empty state
                EmptyStateView(message = "No news articles found.")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = articles,
                        key = { article -> article.title },
                    ) { article ->
                        ArticleRow(
                            article = article,
                            onArticleClick = { onButtonClick(article) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleRow(
    article: Article,
    onArticleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
            ) {
                onArticleClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = article.urlToImage,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = article.title)
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize() // Fill the available space
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) { // Align items in the Row
            Text(text = "Loading...")
            Spacer(modifier = Modifier.width(8.dp)) // Add some space
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ErrorView(errorMessage: String, modifier: Modifier = Modifier) { /* ... */
}

@Composable
fun EmptyStateView(message: String, modifier: Modifier = Modifier) { /* ... */
}

