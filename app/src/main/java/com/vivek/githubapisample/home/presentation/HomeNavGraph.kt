package com.vivek.githubapisample.home.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vivek.githubapisample.common.presentation.NavigationRoute
import com.vivek.githubapisample.repo.presentation.RepoArgs
import com.vivek.githubapisample.repo.presentation.navigateToRepo

/** Route for home graph */
const val HOME_GRAPH_ROUTE = "home_graph"

/**
 * This route is used by Navigation Graph to show Home page
 */
object HomeRoute : NavigationRoute("home")

/**
 * Creates the home graph which consist of [HomePage]
 *
 * @param navController The navigation controller.
 */
fun NavGraphBuilder.homeGraph(navController: NavHostController) {

    navigation(startDestination = HomeRoute.name, route = HOME_GRAPH_ROUTE) {

        // Home Page
        composable(HomeRoute.name) {
            HomePage(onRepoClick = {
                // Move to repo detail page
                navController.navigateToRepo(
                    RepoArgs(
                        name = it.name,
                        owner = checkNotNull(it.owner.login)
                    )
                )
            })
        }
    }
}