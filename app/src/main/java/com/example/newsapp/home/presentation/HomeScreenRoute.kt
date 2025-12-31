package com.example.newsapp.home.presentation

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.newsapp.home.data.Article
import com.example.newsapp.home.data.CardNews

@Composable
fun HomeScreenRoute(
    modifier: Modifier = Modifier,
    onButtonClick: (article: CardNews) -> Unit = {},
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val articles = homeViewModel.headlines.collectAsLazyPagingItems()
    val context = LocalContext.current
    val imageRequest = remember {
        ImageRequest.Builder(context)
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(
            count = articles.itemCount,
            key = articles.itemKey { it.id }
        ) { index ->
            val article = articles[index]
            if (article == null) {
                Column {
                    repeat(5) {
                        NewsCardSkeleton()
                    }
                }
            } else {
                NewsCard(
                    cardNews = article,
                    onCardClick = { onButtonClick(article) },
                    imageRequest = imageRequest.data(article.urlImage)
                        .crossfade(true)
                        .build()
                )
            }
        }

        if (articles.loadState.append is LoadState.Loading) {
            item {
                NewsCardSkeleton()
            }
        }
    }
//    HomeScreenUi(
//        modifier = modifier,
//        onEvent = homeViewModel::onEvent,
//        uiState = uiState,
//        articles = articles.itemSnapshotList
//    )


}

@Composable
fun HomeScreenUi(
    modifier: Modifier,
    onEvent: (UiEvent) -> Unit,
    articles: ItemSnapshotList<CardNews>,
    uiState: HomeUiState
) {
    Surface(
        modifier = modifier.fillMaxSize(),
    )
    {
        // Select Category
        if (uiState.isLoading) {
            LoadingIndicator()
        } else if (uiState.error != null) {
            ErrorView(errorMessage = uiState.error)
        } else {
            val articles = uiState.newsResponse?.articles ?: emptyList()
            if (articles.isEmpty()) { // Handle empty state
                EmptyStateView(message = "No news articles found.")
            } else {
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(
//                        items = articles,
//                        key = { article -> article.title },
//                    ) { article ->
//                        ArticleRow(
//                            article = article,
//                            onArticleClick = { onEvent(UiEvent.UiArticleClick(article)) },
//                        )
//                        if (articles.indexOf(article) < articles.lastIndex) {
//                            Divider(color = Color.LightGray, thickness = 1.dp)
//                        }
//                    }
//                }
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
            ) {
                onArticleClick()
            },
    ) {
        AsyncImage(
            model = article.urlToImage,
            modifier = Modifier,
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
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Loading...")
            Spacer(modifier = Modifier.width(8.dp))
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ErrorView(errorMessage: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Error: $errorMessage")
    }
}

@Composable
fun EmptyStateView(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = message)
    }
}

