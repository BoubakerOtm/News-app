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
import com.example.newsapp.details.presentation.DetailsScreenUi
import com.example.newsapp.details.presentation.DetailsViewModel
import com.example.newsapp.main.presentation.MainScreenUi
import com.example.newsapp.search.presentation.SearchScreenUi
import com.example.newsapp.search.presentation.SearchViewModel

@Composable
fun NewsNavigation(modifier: Modifier) {
    val backStackEntry = rememberNavBackStack(Screens.NestedGraph)
    NavDisplay(
        backStack = backStackEntry,
        onBack = { backStackEntry.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Screens.NestedGraph> {
                MainScreenUi(
                    navigateToDetails = {
                        backStackEntry.add(Screens.DetailsScreen(it))
                    },
                    navigateToSearch = {
                        backStackEntry.add(Screens.Search)
                    },
                )
            }
            entry<Screens.Search> {
                val searchViewModel = hiltViewModel<SearchViewModel>()
                SearchScreenUi(
                    viewModel = searchViewModel,
                    navigateToDetails = {
                        backStackEntry.add(Screens.DetailsScreen(it))
                    },
                    navigateToMain = {
                        backStackEntry.removeLastOrNull()
                    },
                )

            }
            entry<Screens.DetailsScreen> { key ->
                val viewModel = hiltViewModel<DetailsViewModel>()

                DetailsScreenUi(
                    key.article,
                    onBackClick = {
                        backStackEntry.removeLastOrNull()
                    },
                )
            }


        },
    )

}