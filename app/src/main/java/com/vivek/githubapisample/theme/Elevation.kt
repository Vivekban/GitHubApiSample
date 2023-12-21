package com.vivek.githubapisample.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A class that represents different elevation levels used in app.
 *
 * @property low The low elevation level.
 * @property medium The medium elevation level.
 * @property high The high elevation level.
 */
@Suppress("unused")
class Elevation(
    val low: Dp = ElevationDefaults.Low,
    val medium: Dp = ElevationDefaults.Medium,
    val high: Dp = ElevationDefaults.High,
)

/** A default elevation level for card */
val Elevation.card: Dp
    get() = medium

/** A default elevation level for App */
object ElevationDefaults {
    /** Low elevation */
    val Low: Dp = 4.dp

    /** Medium elevation */
    val Medium: Dp = 8.dp

    /** Large elevation */
    val High: Dp = 12.dp

}

/**
 * CompositionLocal used to pass [Elevation] down the tree.
 */
internal val LocalElevation = staticCompositionLocalOf { Elevation() }

/**
 * Retrieves the current [ColorScheme] at the call site's position in the hierarchy.
 */
@Suppress("UnusedReceiverParameter")
val MaterialTheme.elevation: Elevation
    @Composable
    @ReadOnlyComposable
    get() = LocalElevation.current
