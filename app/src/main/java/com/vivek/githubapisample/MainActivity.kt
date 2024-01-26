package com.vivek.githubapisample

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.metrics.performance.JankStats
import androidx.navigation.compose.rememberNavController
import com.vivek.githubapisample.home.presentation.HOME_GRAPH_ROUTE
import com.vivek.githubapisample.theme.GitHubApiSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * The starting activity of the app.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var jankStats: dagger.Lazy<JankStats>

    override fun onCreate(savedInstanceState: Bundle?) {
        if (BuildConfig.DEBUG) {
            enableStrictMode()
        }
        super.onCreate(savedInstanceState)
        setContent {
            GitHubApiSampleTheme {
                val navController = rememberNavController()
                AppNavigationGraph(
                    navController = navController,
                    startDestination = HOME_GRAPH_ROUTE
                )
            }
        }
    }

    private fun enableStrictMode() {
        // Enable strict mode to catch disk and network access on the main thread.
        StrictMode.setThreadPolicy(
            ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )

        // Enable strict mode to catch leaked objects.
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .build()
        )
    }

    override fun onResume() {
        super.onResume()
        jankStats.get().isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
        jankStats.get().isTrackingEnabled = false
    }

}