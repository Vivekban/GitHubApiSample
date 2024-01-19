package com.vivek.githubapisample.common.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vivek.githubapisample.theme.GitHubApiSampleTheme

/**
 * A composable function that displays a loading view.
 *
 * @param modifier The modifier to apply to the loading view.
 */
@Composable
fun Loader(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    GitHubApiSampleTheme {
        Loader()
    }
}