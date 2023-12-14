package com.vivek.githubapisample

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vivek.githubapisample.home.presentation.homeGraph

const val ROOT_GRAPH_ROUTE = "/"

/**
 * The main navigation graph for the app.
 *
 * @param navController The navigation controller to use.
 * @param startDestination The destination to start at.
 */
@Composable
fun AppNavigationGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        route = ROOT_GRAPH_ROUTE,
        startDestination = startDestination
    ) {
        homeGraph(navController)
    }
}