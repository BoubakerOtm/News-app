package com.example.newsapp.app.navigation


import androidx.compose.runtime.saveable.Saver
import androidx.navigation3.runtime.NavKey
import com.example.newsapp.R
import kotlinx.serialization.Serializable

val bottomBarItems = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.Saved
)

@Serializable
sealed class BottomBarScreen(
    val icon: Int,
    val title: String,
) : NavKey {
    @Serializable
    data object Home : BottomBarScreen(
        icon = R.drawable.ic_home,
        title = "Home"
    )


    @Serializable
    data object Saved : BottomBarScreen(
        icon = R.drawable.ic_bookmark,
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