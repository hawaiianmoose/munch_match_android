package com.hawaiianmoose.munchmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.hawaiianmoose.munchmatch.data.DataStoreInitializer
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.view.ListHomeView
import com.hawaiianmoose.munchmatch.view.SignInView
import com.hawaiianmoose.munchmatch.view.SignUpView
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDataStore()
        enableEdgeToEdge()
        setContent {
            MunchMatchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onBackground
                ) {
                    RootNavHost(isUserLoggedIn())
                }
            }
        }
    }
    private fun initializeDataStore() {
        lifecycleScope.launch {
            FirebaseApp.initializeApp(applicationContext)
            DataStoreInitializer.initDataStore(applicationContext)
        }
    }

    private fun isUserLoggedIn(): Boolean {
        return Firebase.auth.currentUser != null
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
        composable("signup") { SignUpView(navController) }
        composable("listhome") { ListHomeView(navController) }
    }
}
