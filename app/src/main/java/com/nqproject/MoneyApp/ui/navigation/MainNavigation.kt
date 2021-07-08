package com.nqproject.MoneyApp.ui.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.nqproject.MoneyApp.ui.screens.GroupDetailsScreen
import com.nqproject.MoneyApp.ui.screens.GroupListScreen
import com.nqproject.MoneyApp.ui.screens.LoginScreen

sealed class MainNavigationScreen(
    val route: String,
) {

    object LoginScreen: MainNavigationScreen(route = "login")
    object Groups: MainNavigationScreen("groups")
    object GroupDetails: MainNavigationScreen("group-details/{groupId}") {
        fun createRoute(groupId: Int): String {
            return "group-details/$groupId"
        }
    }


}

@ExperimentalAnimationApi
@Composable
fun MainNavigation() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = MainNavigationScreen.LoginScreen.route) {

        composable(route = MainNavigationScreen.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(route = MainNavigationScreen.Groups.route) {
            GroupListScreen(navController)
        }

        composable(
            route = MainNavigationScreen.GroupDetails.route,
            arguments = listOf(navArgument("groupId") { type = NavType.IntType }),
        ) { backStackEntry ->
            GroupDetailsScreen(navController, backStackEntry.arguments?.getInt("groupId")!!)
        }

    }

}


