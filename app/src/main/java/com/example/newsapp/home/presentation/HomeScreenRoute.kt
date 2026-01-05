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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.newsapp.home.data.Article
import com.example.newsapp.home.data.CardNews
import com.example.newsapp.ui.theme.NewsAppTheme

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
    HomeScreenUi(
        modifier = modifier,
        onEvent = homeViewModel::onEvent,
        uiState = uiState,
        contentList = {
            ContentList(
                modifier = modifier,
                articles = articles,
                onButtonClick = onButtonClick,
                imageRequest = { urlImage ->
                    imageRequest.data(urlImage)
                        .crossfade(true)
                        .build()
                },
            )
        }
    )
}

@Composable
fun ContentList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<CardNews>,
    onButtonClick: (article: CardNews) -> Unit,
    imageRequest: (String) -> ImageRequest,
) {
    val isRefreshing by remember {
        derivedStateOf { articles.loadState.refresh is LoadState.Loading && articles.itemCount > 0 }
    }
    PullToRefreshBox(
        modifier = Modifier,
        isRefreshing = isRefreshing,
        onRefresh = articles::refresh
    ) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        )
        {
/*            articles.loadState.apply {
                when {
                    // 1. REFRESH STATE: Initial load or pull-to-refresh is in progress
                    refresh is LoadState.Loading -> {
                        item {
                            // Show shimmer skeletons during initial load
                            repeat(5) {
                                NewsCardSkeleton()
                            }
                        }
                    }

                    // 2. REFRESH ERROR: Initial load or refresh failed
                    refresh is LoadState.Error -> {
                        val e = articles.loadState.refresh as LoadState.Error
                        item {
                            ErrorView(
                                errorMessage = "Failed to load articles: ${e.error.localizedMessage}",
                                modifier = Modifier.clickable { articles.retry() }
                            )
                        }
                    }

                    // 4. APPEND ERROR: Loading the next page failed
                    append is LoadState.Error -> {
                        val e = articles.loadState.append as LoadState.Error
                        item {
                            ErrorView(
                                errorMessage = "Failed to load more articles: ${e.error.localizedMessage}",
                                modifier = Modifier.clickable { articles.retry() }
                            )
                        }
                    }
                }
            }*/
            items(
                count = articles.itemCount,
                key = articles.itemKey { it.id }
            ) { index ->
                val article = articles[index]
                article?.let {
                    NewsCard(
                        cardNews = it,
                        onCardClick = { onButtonClick(article) },
                        imageRequest = imageRequest(article.urlImage)
                    )
                }
            }

            if (articles.loadState.refresh is LoadState.NotLoading && articles.itemCount == 0) {
                item {
                    EmptyStateView(message = "No articles found.")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenUiPreview(modifier: Modifier = Modifier) {
    NewsAppTheme {
        HomeScreenUi(
            modifier = modifier,
            onEvent = {},
            uiState = HomeUiState(),
            contentList = {}
        )
    }

}

@Composable
fun HomeScreenUi(
    modifier: Modifier,
    onEvent: (UiEvent) -> Unit,
    uiState: HomeUiState,
    contentList: @Composable () -> Unit,
) {

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        CategoryItemFilterRow(
            selected = uiState.selectedCategory,
            onSelect = { onEvent(UiEvent.CategorySelected(it)) },
        )
        contentList()
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

