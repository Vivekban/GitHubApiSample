package com.vivek.githubapisample.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("unused")
class IconSize(
    val small: Dp = IconSizeDefaults.Small,
    val medium: Dp = IconSizeDefaults.Medium,
    val large: Dp = IconSizeDefaults.Large,
)

object IconSizeDefaults {
    val Small: Dp = 16.dp

    val Medium: Dp = 24.dp

    val Large: Dp = 32.dp
}

internal val LocalIconSize = staticCompositionLocalOf { IconSize() }

/** Retrieves the current [IconSize] at the call site's position in the hierarchy. */
@Suppress("UnusedReceiverParameter")
val MaterialTheme.iconSize: IconSize
    @Composable @ReadOnlyComposable get() = LocalIconSize.current
