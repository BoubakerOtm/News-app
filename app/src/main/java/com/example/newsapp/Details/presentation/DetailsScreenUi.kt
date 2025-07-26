package com.example.newsapp.Details.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.newsapp.home.data.Article

@Composable
fun DetailsScreenUi(
    article: Article,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize().padding(16.dp),
    ) {
        Column(
            modifier = modifier,
        ) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.title)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.description ?: "")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.content ?: "")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.author ?: "")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.publishedAt)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.url)
        }

    }
}

