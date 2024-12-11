package com.hawaiianmoose.munchmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.view.ListHome
import com.hawaiianmoose.munchmatch.view.SignInView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MunchMatchTheme {
                RootNavHost(false) //isUserLoggedIn
            }
        }
    }
}

@Composable
fun RootNavHost(isUserLoggedIn: Boolean) {
    val navController = rememberNavController()
    val startDestination = if (isUserLoggedIn) "listhome" else "signin"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("signin") { SignInView(navController) }
        composable("listhome") { ListHome(navController) }
    }
}
