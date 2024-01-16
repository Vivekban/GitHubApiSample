package com.vivek.githubapisample.common.presentation

import androidx.navigation.NamedNavArgument

/**
 * A class to represent a navigation destination. It will be used to navigate between screens.
 *
 * @property name The route to navigate to.
 * @property title The title of the route.
 */
open class NavigationRoute(
    /**
     * The route to navigate to.
     */
    val name: String,

    /**
     * The title of the route.
     */
    val title: String? = null,

    /** Optional path arguments */
    private val pathArguments: List<NamedNavArgument>? = null,

    /** Optional query arguments */
    private val queryArguments: List<NamedNavArgument>? = null,
) {

    /** Final destination along with [name], [pathArguments] and [queryArguments] */
    @Suppress("UNUSED")
    val destination
        get() = buildString {
            append(name)
            append(argumentsAsPath)
        }

    /** Return arguments as string */
    val argumentsAsPath = buildString {
        pathArguments?.forEach { arg ->
            append("/{${arg.name}}")
        }
        queryArguments?.let { arguments ->
            append("?")
            append(arguments.map { arg -> "${arg.name}={${arg.name}}" }.joinToString { "," })
        }
    }

    @Suppress("UNUSED")
            /** Return collective list */
    val arguments
        get() = listOf(pathArguments, queryArguments).flatMap {
            it?.asIterable() ?: emptyList()
        }

}