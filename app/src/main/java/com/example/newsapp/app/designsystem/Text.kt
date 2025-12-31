package com.example.newsapp.app.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BodyText(modifier: Modifier = Modifier, data: String) {
    Text(
        text = data,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
fun BodyTextPreview(modifier: Modifier = Modifier) {
    Column(

    ) {
        AppTitleText(data = "AppTitleText")
        ArticleTitleText(data = "ArticleTitleText")
        CardTitleText(data = "CardTitleText")
        BodyText(data = "BodyText")
        CaptionText(data = "CaptionText")
        BadgeText(data = "BadgeText")
    }
}

@Composable
fun AppTitleText(modifier: Modifier = Modifier, data: String) {
    Text(
        text = data,
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
fun ArticleTitleText(modifier: Modifier = Modifier, data: String) {
    Text(
        text = data,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
fun CardTitleText(modifier: Modifier = Modifier, data: String) {
    Text(
        text = data,
        style = MaterialTheme.typography.titleSmall,
    )
}

@Composable
fun CaptionText(modifier: Modifier = Modifier, data: String) {
    Text(
        text = data,
        style = MaterialTheme.typography.bodySmall,
    )
}

@Composable
fun BadgeText(modifier: Modifier = Modifier, data: String) {
    Text(
        text = data,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onPrimary
    )
}