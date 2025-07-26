package com.example.newsapp.app.navigation

import androidx.navigation3.runtime.NavKey
import com.example.newsapp.home.data.Article
import kotlinx.serialization.Serializable

sealed class Screens : NavKey {
    @Serializable
    data object HomeScreen : Screens()
    @Serializable
    data class DetailsScreen(val article: Article) : Screens()
}