package com.vivek.githubapisample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.vivek.githubapisample.home.presentation.HOME_GRAPH_ROUTE
import com.vivek.githubapisample.ui.navigation.AppNavigationGraph
import com.vivek.githubapisample.ui.theme.GitHubApiSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
}