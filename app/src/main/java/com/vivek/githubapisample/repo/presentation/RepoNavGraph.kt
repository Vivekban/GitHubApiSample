package com.vivek.githubapisample.repo.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.vivek.githubapisample.common.presentation.NavigationRoute

/** Route for repo graph */
private const val REPO_GRAPH_ROUTE = "repo_graph"

private const val NAME_ARG: String = "name"

private const val OWNER_ARG: String = "owner"

/** Arguments required for repo page */
data class RepoArgs(val owner: String, val name: String) {
    constructor(saveStateHandle: SavedStateHandle) : this(
        checkNotNull(saveStateHandle[OWNER_ARG]), checkNotNull(saveStateHandle[NAME_ARG])
    )
}

/** Helper method to pass arguments to Repo navigation */
private fun RepoArgs.asPath() = "/$owner/$name"

/** This route is used by Navigation Graph to show [RepoPage] */
private object RepoRoute : NavigationRoute(
    "repo", pathArguments = listOf(
        navArgument(OWNER_ARG) {
            type = NavType.StringType
        },
        navArgument(NAME_ARG) {
            type = NavType.StringType
        },
    )
)

/** Navigates to the repo screen */
fun NavController.navigateToRepo(args: RepoArgs, navOptions: NavOptions? = null) =
    navigate(REPO_GRAPH_ROUTE + args.asPath(), navOptions = navOptions)

/**
 * Creates the repo graph which consist of [RepoPage]
 */
fun NavGraphBuilder.repoGraph(navController: NavHostController) {

    navigation(
        startDestination = RepoRoute.name,
        route = "$REPO_GRAPH_ROUTE${RepoRoute.argumentsAsPath}",
    ) {

        // Repo Detail page
        composable(
            RepoRoute.name,
        ) {
            val viewModel: RepoViewModel = hiltViewModel()
            RepoPage(viewModel = viewModel, onBackClick = {
                navController.popBackStack()
            })
        }
    }
}