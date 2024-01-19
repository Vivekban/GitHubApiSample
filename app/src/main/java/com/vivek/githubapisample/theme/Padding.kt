package com.vivek.githubapisample.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A class that represents padding values used in app.
 *
 * @property extraSmall The extra small padding value.
 * @property small The small padding value.
 * @property medium The medium padding value.
 * @property large The large padding value.
 */
@Suppress("unused")
class Padding(
    val extraSmall: Dp = PaddingDefaults.ExtraSmall,
    val small: Dp = PaddingDefaults.Small,
    val medium: Dp = PaddingDefaults.Medium,
    val large: Dp = PaddingDefaults.Large,
)

/** Default values for padding. */
object PaddingDefaults {
    /** Extra small sized padding */
    val ExtraSmall: Dp = 4.dp

    /** Small sized padding */
    val Small: Dp = 8.dp

    /** Medium sized padding */
    val Medium: Dp = 16.dp

    /** Large sized padding */
    val Large: Dp = 20.dp

}

/**
 * CompositionLocal used to pass [Padding] down the tree.
 */
internal val LocalPadding = staticCompositionLocalOf { Padding() }

/**
 * Helper method which retrieves the current [Padding] at the call site's position in the hierarchy.
 */
@Suppress("UnusedReceiverParameter")
val MaterialTheme.padding: Padding
    @Composable
    @ReadOnlyComposable
    get() = LocalPadding.current
