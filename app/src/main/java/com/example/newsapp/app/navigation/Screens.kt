package com.example.newsapp.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.navigation3.runtime.NavKey
import com.example.newsapp.home.data.Article
import kotlinx.serialization.Serializable

sealed class Screens : NavKey {
    @Serializable
    data object NestedGraph : Screens()
    @Serializable
    data object Search : Screens()
    @Serializable
    data class DetailsScreen(val article: Article) : Screens()
}