package com.vivek.githubapisample.common.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource

/**
 * A wrapper class around [StringRes] and [String] which simplifies displaying error message to user.
 *
 * Useful in display error messages to the user which is usually combination of
 * predefined error in (StringRes) form and unknown server error in (String).
 *
 * This class exposes a [toDisplayString] method which returns actual text to display user.
 *
 * For example,
 *
 * ```
 * class CounterModel: ViewModel() {
 *     val message = MutableStateFlow<UiString?>(null)
 *
 *     init {
 *         viewModelScope.launch {
 *             message.value = R.string.something_went_wrong.ui
 *             delay(100)
 *             message.value = "Input Error".ui
 *         }
 *     }
 * }
 *
 * @Composable
 * fun Page(model: CounterModel) {
 *
 *     val message by model.message.collectAsState()
 *     val hostState = remember { SnackbarHostState() }
 *
 *     val text = message?.text()
 *
 *     LaunchedEffect(key1 = message) {
 *         text?.let {
 *             hostState.showSnackbar(
 *                 message = it
 *             )
 *         }
 *     }
 * }
 * ```
 */
sealed class UiString {

    /**
     * A data class that represents a resource string.
     *
     * @property stringId The resource ID of the string to be displayed.
     */
    data class Resource(@StringRes val stringId: Int) : UiString()

    /**
     * A data class that represents a dynamic string example error from server.
     *
     * @property text The text to be displayed.
     */
    data class Text(val text: String) : UiString()

}

/**
 * A helper composable method provides the text of [UiString].
 *
 * @return The content of this UI text.
 */
@Composable
@ReadOnlyComposable
fun UiString.toDisplayString(): String = when (this) {
    is UiString.Resource -> stringResource(id = stringId)
    is UiString.Text -> text
}

/** Helper method convert any string to UIString */
inline val String.uiString: UiString get() = UiString.Text(this)

/** Helper method convert any [StringRes] to UIString */
inline val Int.uiString: UiString get() = UiString.Resource(this)

