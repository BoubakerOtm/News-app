package com.example.newsapp.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.newsapp.R
import com.example.newsapp.app.navigation.BottomBarScreen
import com.example.newsapp.app.navigation.BottomBarScreenSaver
import com.example.newsapp.app.navigation.bottomBarItems
import com.example.newsapp.home.data.Article
import com.example.newsapp.home.data.CardNews
import com.example.newsapp.home.presentation.HomeScreenRoute
import com.example.newsapp.home.presentation.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenUi(
    modifier: Modifier = Modifier,
    navigateToDetails: (article: CardNews) -> Unit,
    navigateToSearch: () -> Unit
) {
    val backStack = rememberNavBackStack(BottomBarScreen.Home)

    var currentBottomBarScreen: BottomBarScreen by rememberSaveable(
        stateSaver = BottomBarScreenSaver,
    ) { mutableStateOf(BottomBarScreen.Home) }

    Scaffold(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
            ),
        topBar = {
            TopAppBar(
                title = { Text("News App") },
                actions = {
                    IconButton(onClick = navigateToSearch) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = "Search",
                        )
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                bottomBarItems.forEach { destination ->
                    NavigationBarItem(
                        selected = currentBottomBarScreen == destination,
                        icon = {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = destination.title
                            )
                        },
                        label = { Text(destination.title) },
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
                rememberSaveableStateHolderNavEntryDecorator(),
//                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<BottomBarScreen.Home> {
                    HomeScreenRoute(modifier = modifier) {
                        navigateToDetails(it)
                    }

                }
                entry<BottomBarScreen.Saved> {

                }

            },
        )
    }
}