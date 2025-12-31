package com.example.newsapp.details.presentation

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.example.newsapp.home.data.Article
import com.example.newsapp.home.data.CardNews

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenUi(
    article: CardNews,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Details")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }

                },
            )
        },
        modifier = modifier.padding(horizontal = 8.dp),
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(state),
        ) {
            AsyncImage(
                model = article.urlImage,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Description")
            Text(text = article.description)
            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "Content")
//            Text(text = article.content ?: "")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Author")
            Text(text = article.caption)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Published At")
            Text(text = article.date)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Source")
            Spacer(modifier = Modifier.height(8.dp))
            ArticleLink(url = article.id)
        }
    }
}

@Composable
fun ArticleLink(url: String) {
    val context = LocalContext.current
    val annotatedText = buildAnnotatedString {
        pushStringAnnotation(tag = "URL", annotation = url)
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
            ),
        ) {
            append(url)
        }
        pop()
    }
    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { stringAnnotation ->
                    val intent = Intent(Intent.ACTION_VIEW, stringAnnotation.item.toUri())
                    context.startActivity(intent)
                }
        },
    )
}