package com.miftah.sehaty.ui.screens.navigator

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miftah.sehaty.R
import com.miftah.sehaty.domain.model.FoodAfterScan
import com.miftah.sehaty.domain.model.convertToFoodAfterScan
import com.miftah.sehaty.ui.screens.detail.DetailEvent
import com.miftah.sehaty.ui.screens.detail.DetailScreen
import com.miftah.sehaty.ui.screens.detail.DetailViewModel
import com.miftah.sehaty.ui.screens.history.HistoryEvent
import com.miftah.sehaty.ui.screens.history.HistoryScreen
import com.miftah.sehaty.ui.screens.history.HistoryViewModel
import com.miftah.sehaty.ui.screens.navGraph.Route
import com.miftah.sehaty.ui.screens.navigator.navigators.BottomNavigationItem
import com.miftah.sehaty.ui.screens.navigator.navigators.MainBottomBar
import com.miftah.sehaty.ui.screens.navigator.navigators.MainTopBar
import com.miftah.sehaty.ui.screens.scan.ScanEvent
import com.miftah.sehaty.ui.screens.scan.ScanScreen
import com.miftah.sehaty.ui.screens.scan.ScanViewModel
import com.miftah.sehaty.ui.screens.setting.SettingData
import com.miftah.sehaty.ui.screens.setting.SettingScreen
import com.miftah.sehaty.ui.screens.setting.SettingsViewModel
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.utils.Constant.FOOD_AFTER_SCAN
import com.miftah.sehaty.utils.Constant.FOOD_URI
import com.miftah.sehaty.utils.Constant.IS_FROM_HISTORY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigator(
    startDestination: Route = Route.HistoryScreen,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavigationItem(R.drawable.home_icon, "Home"),
        BottomNavigationItem(null, ""),
        BottomNavigationItem(R.drawable.profile_icon, "Profile"),
    )
    var selectedItem by remember { mutableIntStateOf(0) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val backStackState = navController.currentBackStackEntryAsState().value

    val title by remember(backStackState) {
        derivedStateOf {
            when (backStackState?.destination?.route) {
                Route.HistoryScreen.route -> "Home"
                Route.ScanScreen.route -> "Scan Screen"
                Route.SettingScreen.route -> "Settings"
                Route.DetailScreen().route -> "Detail"
                else -> "Err"
            }
        }
    }

    val isBottomBarVisible by remember(backStackState) {
        derivedStateOf {
            backStackState?.destination?.route == Route.HomeScreen.route ||
                    backStackState?.destination?.route == Route.HistoryScreen.route ||
                    backStackState?.destination?.route == Route.SettingScreen.route
        }
    }

    val whichTopAppBar by remember(backStackState) {
        derivedStateOf {
            when (backStackState?.destination?.route) {
                Route.HistoryScreen.route -> 1
                Route.ScanScreen.route -> 2
                Route.SettingScreen.route -> 3
                Route.DetailScreen().route -> 4
                else -> 69
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                /*MainBottomBar(
                    modifier = Modifier.padding(8.dp),
                    items = items,
                    onSelectedChange = {
                        selectedItem = it
                        when (it) {
                            0 -> navController.navigate(Route.HistoryScreen.route)
                            1 -> navController.navigate(Route.ScanScreen.route)
                            2 -> navController.navigate(Route.SettingScreen.route)
                        }
                    },
                    selectedItem = selectedItem

                )*/
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    MainBottomBar(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        items = items,
                        onSelectedChange = {
                            selectedItem = it
                            when (it) {
                                0 -> navController.navigate(Route.HistoryScreen.route)
                                2 -> navController.navigate(Route.SettingScreen.route)
                            }
                        },
                        selectedItem = selectedItem
                    )

                    IconButton(
                        modifier = Modifier
                            .padding(bottom = 35.dp)

                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .align(Alignment.TopCenter)
                            .padding(10.dp),
                        onClick = { navController.navigate(Route.ScanScreen.route) },
                    ) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = R.drawable.scan),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        },
        topBar = {
            when (whichTopAppBar) {
                1, 3 -> {
                    MainTopBar(
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//                        actions = {
//                            IconButton(onClick = {
//                                navController.navigate(Route.SettingScreen.route)
//                            }) {
//                                Icon(
//                                    imageVector = Icons.Default.Settings,
//                                    contentDescription = "Localized description"
//                                )
//                            }
//                        },

                        scrollBehavior = scrollBehavior,
                        title = title
                    )
                }

                else -> {}
            }

        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.surface
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination.route,
            ) {
                composable(route = Route.HistoryScreen.route) {
                    val viewModel: HistoryViewModel = hiltViewModel()
                    HistoryScreen(
                        state = viewModel.state.value,
                        event = viewModel::onEvent,
                        navigateToDetail = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = FOOD_AFTER_SCAN,
                                value = it.convertToFoodAfterScan()
                            )
                            val route =
                                Route.DetailScreen("1").goto
                            navController.navigate(route)
                        }
                    )
                }
                composable(route = Route.ScanScreen.route) {
                    val viewModel: ScanViewModel = hiltViewModel()
                    val goToDetail: (FoodAfterScan) -> Unit = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = FOOD_AFTER_SCAN,
                            value = it
                        )
                        val route = Route.DetailScreen("0").goto
                        navController.navigate(route)
                    }
                    ScanScreen(
                        state = viewModel.scanState.value,
                        onEvent = viewModel::onEvent,
                        navigateToDetail = goToDetail,
                    ) {
                        navController.popBackStack()
                    }
                }
                composable(route = Route.SettingScreen.route) {
                    val viewModel: SettingsViewModel = hiltViewModel()

                    SettingScreen(
                        state = viewModel.settingsState.value,
                        onEvent = viewModel::onEvent
                    )
                }
                composable(
                    route = Route.DetailScreen().route,
                    arguments = listOf(navArgument(
                        name = IS_FROM_HISTORY,
                        builder = {
                            type = NavType.StringType
                        }
                    ))
                ) { backStackState ->
                    val isFromHistory =
                        backStackState.arguments?.getString(IS_FROM_HISTORY)

                    val result =
                        navController.previousBackStackEntry?.savedStateHandle?.get<FoodAfterScan>(
                            FOOD_AFTER_SCAN
                        )
                    val viewModel: DetailViewModel = hiltViewModel()
                    if (result != null) {
                        viewModel.setDetailState(result)
                    }
                    DetailScreen(
                        state = viewModel.detailState.value,
                        onEvent = viewModel::onEvent,
                        isFromHistory = when (isFromHistory) {
                            "1" -> true
                            "0" -> false
                            else -> false
                        }
                    ) {
                        navController.navigate(Route.HistoryScreen.route) {
                            popUpTo(0)
                        }
                    }
                }
            }
        }
    }
}