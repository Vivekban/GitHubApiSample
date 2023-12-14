package com.vivek.githubapisample.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("unused")
class Padding(
    val extraSmall: Dp = PaddingDefaults.ExtraSmall,
    val small: Dp = PaddingDefaults.Small,
    val medium: Dp = PaddingDefaults.Medium,
    val large: Dp = PaddingDefaults.Large,
    val extraLarge: Dp = PaddingDefaults.ExtraLarge,
)

object PaddingDefaults {
    /** Extra small sized padding */
    val ExtraSmall: Dp = 4.dp

    /** Small sized padding */
    val Small: Dp = 8.dp

    /** Medium sized padding */
    val Medium: Dp = 16.dp

    /** Large sized padding */
    val Large: Dp = 20.dp

    /** Extra large sized padding */
    val ExtraLarge: Dp = 24.dp
}

internal val LocalPadding = staticCompositionLocalOf { Padding() }

/**
 * Retrieves the current [ColorScheme] at the call site's position in the hierarchy.
 */
val MaterialTheme.padding: Padding
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current
