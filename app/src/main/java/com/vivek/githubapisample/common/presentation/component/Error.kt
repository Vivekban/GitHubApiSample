package com.vivek.githubapisample.common.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.vivek.githubapisample.theme.GitHubApiSampleTheme

/**
 * A composable function that displays an error view.
 *
 * @param title The title of the error view.
 * @param modifier The modifier to apply to the error view.
 */
@Composable
fun Error(title: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview() {
    GitHubApiSampleTheme {
        Error(title = "Error Found")
    }
}