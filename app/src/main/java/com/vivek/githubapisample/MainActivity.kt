package com.vivek.githubapisample

import android.os.Bundle
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

    override fun onResume() {
        super.onResume()
        jankStats.get().isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
        jankStats.get().isTrackingEnabled = false
    }

}