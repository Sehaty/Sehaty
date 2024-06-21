package com.miftah.sehaty.ui.screens.navigator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.miftah.sehaty.R
import com.miftah.sehaty.domain.model.FoodAfterScan
import com.miftah.sehaty.domain.model.convertToFoodAfterScan
import com.miftah.sehaty.ui.screens.detail.DetailEvent
import com.miftah.sehaty.ui.screens.detail.DetailScreen
import com.miftah.sehaty.ui.screens.detail.DetailViewModel
import com.miftah.sehaty.ui.screens.history.HistoryScreen
import com.miftah.sehaty.ui.screens.history.HistoryViewModel
import com.miftah.sehaty.ui.screens.navGraph.Route
import com.miftah.sehaty.ui.screens.navigator.navigators.BottomNavigationItem
import com.miftah.sehaty.ui.screens.navigator.navigators.MainBottomBar
import com.miftah.sehaty.ui.screens.scan.ScanScreen
import com.miftah.sehaty.ui.screens.scan.ScanViewModel
import com.miftah.sehaty.ui.screens.setting.SettingData
import com.miftah.sehaty.ui.screens.setting.SettingScreen
import com.miftah.sehaty.ui.theme.SehatyTheme
import com.miftah.sehaty.utils.Constant.FOOD_AFTER_SCAN
import com.miftah.sehaty.utils.Constant.FOOD_URI

@Composable
fun MainNavigator(
    startDestination: Route = Route.HistoryScreen,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavigationItem(Icons.Default.History, "Scan History"),
        BottomNavigationItem(Icons.Default.DocumentScanner, "Scan"),
        BottomNavigationItem(Icons.Default.Settings, "Setting"),
    )
    var selectedItem by remember { mutableIntStateOf(0) }

    val backStackState = navController.currentBackStackEntryAsState().value

    val isBottomBarVisible by remember(backStackState) {
        derivedStateOf {
            backStackState?.destination?.route == Route.HomeScreen.route ||
                    backStackState?.destination?.route == Route.HistoryScreen.route ||
                    backStackState?.destination?.route == Route.SettingScreen.route
        }
    }

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                MainBottomBar(
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
                )
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

 /*                   viewModel.isAccountActive().collectAsState(initial = false).value.let {
                        viewModel.searchHistory(it)
                    }*/

                    HistoryScreen(
                        state = viewModel.state.value,
                        event = viewModel::onEvent,
                        navigateToDetail = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = FOOD_AFTER_SCAN,
                                value = it.convertToFoodAfterScan()
                            )
                            navController.navigate(Route.DetailScreen.route)
                        }
                    )
                }
                composable(route = Route.ScanScreen.route) {
                    val viewModel: ScanViewModel = hiltViewModel()
                    val goToDetail: (FoodAfterScan) -> Unit = {
//                        navController.navigate("${Route.DetailScreen.route}/${it}")
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = FOOD_AFTER_SCAN,
                            value = it
                        )
                        navController.navigate(Route.DetailScreen.route)
                    }
                    ScanScreen(
                        state = viewModel.scanState.value,
                        onEvent = viewModel::onEvent,
                        navigateToDetail = goToDetail,
                    ){
                        navController.popBackStack()
                    }
                }
                composable(route = Route.SettingScreen.route) {
                    SettingScreen(
                        itemSettings = listOf(
                            SettingData(
                                title = "WhatsApp",
                                description = "Connect to WhatsApp",
                                drawable = R.drawable.whatsapp_ic
                            )
                        )
                    ) {
                        // TO WA
                    }
                }
                composable(route = Route.DetailScreen.route) {
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
                        onEvent = viewModel::onEvent
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