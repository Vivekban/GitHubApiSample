package com.vivek.githubapisample.home.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.vivek.githubapisample.repo.presentation.RepoPage
import com.vivek.githubapisample.repo.presentation.RepoRoute

const val HOME_GRAPH_ROUTE = "home_graph"

/**
 *
 */
fun NavGraphBuilder.homeGraph(navController: NavHostController) {

    navigation(startDestination = HomeRoute.route, route = HOME_GRAPH_ROUTE) {

        composable(HomeRoute.route) {
            HomePage(onRepoClick = {
                navController.navigate(RepoRoute.withArgs(it.owner.login ?: "", it.name))
            })
        }

        composable(
            RepoRoute.route + "/{owner}/{name}",
            arguments = listOf(
                navArgument("owner") {
                    type = NavType.StringType
                },
                navArgument("name") {
                    type = NavType.StringType
                }
            )) {
            RepoPage(onBackClick = {
                navController.popBackStack()
            })
        }
    }
}