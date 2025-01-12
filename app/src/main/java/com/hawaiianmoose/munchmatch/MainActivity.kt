package com.hawaiianmoose.munchmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import com.hawaiianmoose.munchmatch.data.DataStoreInitializer
import com.hawaiianmoose.munchmatch.ui.theme.MunchMatchTheme
import com.hawaiianmoose.munchmatch.view.RootNavHost
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