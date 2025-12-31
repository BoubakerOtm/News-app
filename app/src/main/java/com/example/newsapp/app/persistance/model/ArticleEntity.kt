package com.example.newsapp.app.persistance.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.home.data.Article
import com.example.newsapp.home.data.CardNews

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    val url: String,
    val source: String,
    val author: String?,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val publishedAt: String,
    val content: String?
)

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        url = url,
        source = source.name,
        author = author,
        title = title,
        description = description,
        imageUrl = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

fun ArticleEntity.toCardNews(): CardNews {
    return CardNews(
        id = url,
        urlImage = imageUrl ?: "",
        cardTitle = title,
        caption = description ?: "",
        date = publishedAt,
        description = content ?: "",
        badge = source
    )
}

