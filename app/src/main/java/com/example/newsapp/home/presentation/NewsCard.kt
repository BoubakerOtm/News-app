package com.example.newsapp.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.newsapp.R
import com.example.newsapp.app.designsystem.BadgeText
import com.example.newsapp.app.designsystem.BodyText
import com.example.newsapp.app.designsystem.CaptionText
import com.example.newsapp.app.designsystem.CardTitleText
import com.example.newsapp.home.data.CardNews
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    cardNews: CardNews,
    onCardClick: () -> Unit = {},
    imageRequest: ImageRequest
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(
            12.dp
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onCardClick)
        ) {
            Box {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = cardNews.cardTitle,
                    placeholder = painterResource(id = R.drawable.ic_home),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
                if (cardNews.badge.isNotEmpty())
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.error,
                                shape = RoundedCornerShape(
                                    16.dp
                                )
                            )
                            .padding(
                                vertical = 5.dp,
                                horizontal = 12.dp
                            )
                    ) {
                        BadgeText(
                            data = cardNews.badge
                        )
                    }
            }
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    Arrangement.spacedBy(5.dp),

                    ) {
                    CaptionText(
                        data = cardNews.badge
                    )
                    CaptionText(
                        data = ":"
                    )
                    CaptionText(
                        data = cardNews.date
                    )

                }
                CardTitleText(
                    data = cardNews.cardTitle
                )
                BodyText(
                    data = cardNews.description
                )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun NewsCardPreview() {
    NewsAppTheme{
//        NewsCard(
//            cardNews = CardNews(
////            urlImage = ,
//                id = "1",
//                description = "Global stock markets experienced significant gains today as major technology companies reported better-than-expected earnings. The rally comes amid renewed investor confidence in the tech sector and positive eco",
//                cardTitle = "Card title",
//                date = "about 5 hours ago",
//                caption = "Financial Times",
//                badge = "Breaking News"
//            ),
//        )
    }

}