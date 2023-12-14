package com.vivek.githubapisample.common.presentation

/**
 * A class to represent a navigation destination.
 */
open class NavigationRoute(
    /**
     * The route to navigate to.
     */
    val route: String,

    /**
     * The title of the route.
     */
    val title: String? = null,
) {
    // A function to add arguments to the route.
    fun withArgs(vararg args: String): String {
        // Create a StringBuilder to build the route with arguments.
        val builder = StringBuilder()

        // Append the route to the StringBuilder.
        builder.append(route)

        // Iterate over the arguments and append them to the StringBuilder.
        args.forEach { arg ->
            // Append a "/" before each argument.
            builder.append("/$arg")
        }

        // Return the built route.
        return builder.toString()
    }

}