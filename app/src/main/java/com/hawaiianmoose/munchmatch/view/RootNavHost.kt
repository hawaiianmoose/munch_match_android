package com.hawaiianmoose.munchmatch.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hawaiianmoose.munchmatch.model.EateryList
import com.hawaiianmoose.munchmatch.model.MatchSession
import kotlinx.serialization.json.Json

@Composable
fun RootNavHost(isUserLoggedIn: Boolean) {
    val navController = rememberNavController()
    val startDestination = if (isUserLoggedIn) "listhome" else "signin"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = "signin") { SignInView(navController) }
        composable(route = "signup") { SignUpView(navController) }
        composable(route = "listhome") { ListHomeView(navController) }
        composable(route = "accountsettings") { AccountSettingsView(navController) }
        composable(
            route = "listdetail/{listJson}",
            arguments = listOf(navArgument("listJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val listJson = backStackEntry.arguments?.getString("listJson")
            val selectedList = listJson?.let { Json.decodeFromString<EateryList>(it) }
            selectedList?.let {
                ListDetailView(it, navController)
            }
        }
        composable(
            route = "lobby/{listJson}",
            arguments = listOf(navArgument("listJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val listJson = backStackEntry.arguments?.getString("listJson")
            val selectedList = listJson?.let { Json.decodeFromString<EateryList>(it) }
            selectedList?.let {
                LobbyView(it, navController)
            }
        }
        composable(
            route = "match/{sessionJson}",
            arguments = listOf(navArgument("sessionJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val listJson = backStackEntry.arguments?.getString("sessionJson")
            val selectedList = listJson?.let { Json.decodeFromString<MatchSession>(it) }
            selectedList?.let {
                MatchingView(it, navController)
            }
        }
        composable(
            route = "results/{sessionJson}",
            arguments = listOf(navArgument("sessionJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val listJson = backStackEntry.arguments?.getString("sessionJson")
            val selectedList = listJson?.let { Json.decodeFromString<MatchSession>(it) }
            selectedList?.let {
                ResultView(it, navController)
            }
        }
    }
}