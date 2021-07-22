package com.nqproject.MoneyApp.ui.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.nqproject.MoneyApp.manager.AuthenticationManager
import com.nqproject.MoneyApp.repository.Group
import com.nqproject.MoneyApp.ui.screens.GroupDetailsScreen
import com.nqproject.MoneyApp.ui.screens.GroupListScreen
import com.nqproject.MoneyApp.ui.screens.LoginScreen
import com.nqproject.MoneyApp.ui.screens.add_group.AddGroupScreen
import com.nqproject.MoneyApp.ui.screens.auth.registration.RegistrationScreen

sealed class MainNavigationScreen(
    val route: String,
) {

    object LoginScreen: MainNavigationScreen(route = "login")
    object Groups: MainNavigationScreen("groups")
    object AddGroups: MainNavigationScreen("add-groups")
    object RegistrationScreen: MainNavigationScreen("registration")

    object GroupDetails: MainNavigationScreen("group-details/{group}") {
        fun createRoute(group: Group): String {
            val json = Gson().toJson(group)
            return "group-details/$json"
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun MainNavigation() {

    val navController = rememberNavController()
    val startDestination = if (AuthenticationManager.token != null) MainNavigationScreen.Groups.route else MainNavigationScreen.LoginScreen.route

    NavHost(navController, startDestination = startDestination) {

        composable(route = MainNavigationScreen.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(route = MainNavigationScreen.RegistrationScreen.route) {
            RegistrationScreen(navController)
        }

        composable(route = MainNavigationScreen.Groups.route) {
            GroupListScreen(navController)
        }

        composable(
            route = MainNavigationScreen.GroupDetails.route,
            arguments = listOf(navArgument("group") { type = NavType.StringType }),
        ) { backStackEntry ->
            val jsonGroup = backStackEntry.arguments?.getString("group")!!
            val group = Gson().fromJson(jsonGroup, Group::class.java)
            GroupDetailsScreen(navController, group)
        }

        composable(
            route = MainNavigationScreen.AddGroups.route,
        ) {
            AddGroupScreen(navController = navController)
        }
    }
}


