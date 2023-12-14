package com.vivek.githubapisample.ui.navigation

import androidx.annotation.DrawableRes

open class NavigationRoute(
    val route: String,
    val title: String? = null,
) {
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}