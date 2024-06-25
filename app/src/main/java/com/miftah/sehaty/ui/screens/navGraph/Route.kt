package com.miftah.sehaty.ui.screens.navGraph

import androidx.navigation.NamedNavArgument
import com.miftah.sehaty.utils.Constant.FOOD_AFTER_SCAN
import com.miftah.sehaty.utils.Constant.IS_FROM_HISTORY

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
    val goto : String = ""
) {
    data object OnBoardingApp : Route(route = "onBoardingScreen")

    data object AppStartNavigation : Route(route = "appStartNavigation")

    data object HomeScreen : Route(route = "homeScreen")

    data object HistoryScreen : Route(route = "historyScreen")

    data object ScanScreen : Route(route = "scanScreen")

    data object SettingScreen : Route(route = "settingScreen")

    data class DetailScreen(val isFromHistory: String = "1") :
        Route(route = "detailScreen/{$IS_FROM_HISTORY}", goto = "detailScreen/$isFromHistory")

    data object MainNavigator : Route(route = "mainNavigator")
}