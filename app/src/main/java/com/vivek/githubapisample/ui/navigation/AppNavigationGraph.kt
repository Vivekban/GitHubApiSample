package com.vivek.githubapisample.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vivek.githubapisample.home.presentation.homeGraph

const val ROOT_GRAPH_ROUTE = "/"

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