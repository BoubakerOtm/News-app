package com.example.newsapp.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

val bottomBarItems = listOf(
    BottomBarScreen.Home,
//    BottomBarScreen.Search,
    BottomBarScreen.Saved
)

@Serializable
sealed class BottomBarScreen(
    @Contextual val icon: ImageVector,
    val title: String,
): NavKey {
    @Serializable
    data object Home : BottomBarScreen(
        icon = Icons.Default.Home,
        title = "Home"
    )



    @Serializable
    data object Saved : BottomBarScreen(
        icon = Icons.Default.Star,
        title = "Saved"
    )
}

val BottomBarScreenSaver = Saver<BottomBarScreen, String>(
    save = { it::class.simpleName ?: "Unknown" },
    restore = {
        when (it) {
            BottomBarScreen.Home::class.simpleName -> BottomBarScreen.Home
            BottomBarScreen.Saved::class.simpleName -> BottomBarScreen.Saved
            else -> BottomBarScreen.Home
        }
    }
)