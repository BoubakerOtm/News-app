package com.example.newsapp.main.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.newsapp.app.navigation.BottomBarScreen
import com.example.newsapp.app.navigation.BottomBarScreenSaver
import com.example.newsapp.app.navigation.Screens
import com.example.newsapp.app.navigation.bottomBarItems
import com.example.newsapp.home.data.Article
import com.example.newsapp.home.presentation.HomeScreenUi
import com.example.newsapp.home.presentation.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenUi(modifier: Modifier = Modifier, navigateToDetails: (article: Article) -> Unit , navigateToSearch: () -> Unit) {
    val backStack = rememberNavBackStack(BottomBarScreen.Home)

    var currentBottomBarScreen: BottomBarScreen by rememberSaveable(
        stateSaver = BottomBarScreenSaver,
    ) { mutableStateOf(BottomBarScreen.Home) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("News App") },
                actions = {
                    IconButton(onClick = navigateToSearch ) {
                        Icon(
                           Icons.Filled.Search,
                            contentDescription = "Search",
                        )
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier,
                containerColor = Color.White,

            ) {
                bottomBarItems.forEach { destination ->
                    NavigationBarItem(
                        selected = currentBottomBarScreen == destination,
                        icon = {
                            Icon(destination.icon, contentDescription = destination.title)
                        },
                        onClick = {
                            if (backStack.lastOrNull() != destination) {
                                if (backStack.lastOrNull() in bottomBarItems) {
                                    backStack.removeAt(backStack.lastIndex)
                                }
                                backStack.add(destination)
                                currentBottomBarScreen = destination
                            }
                        },
                    )
                }
            }

        },
        ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                rememberSceneSetupNavEntryDecorator(),
                rememberSavedStateNavEntryDecorator(),
//                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<BottomBarScreen.Home> {
                    val homeViewModel = hiltViewModel<HomeViewModel>()
                    HomeScreenUi(homeViewModel, modifier) {
                        navigateToDetails(it)
                    }

                }
                entry<BottomBarScreen.Saved> {

                }

            },
        )
    }
}