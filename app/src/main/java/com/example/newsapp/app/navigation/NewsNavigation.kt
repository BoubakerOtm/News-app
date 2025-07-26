package com.example.newsapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.newsapp.Details.presentation.DetailsScreenUi
import com.example.newsapp.home.presentation.HomeScreenUi
import com.example.newsapp.home.presentation.HomeViewModel

@Composable
fun NewsNavigation(modifier: Modifier) {
    val backStackEntry = rememberNavBackStack(Screens.HomeScreen)
    NavDisplay(
        backStack = backStackEntry,
        onBack = { backStackEntry.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Screens.HomeScreen> {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                HomeScreenUi(homeViewModel, modifier) {
                    backStackEntry.add(Screens.DetailsScreen(article = it))
                }
            }
            entry<Screens.DetailsScreen> { key ->
                DetailsScreenUi(key.article, modifier)
            }
        },
    )

}