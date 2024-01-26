package com.vivek.githubapisample.common.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vivek.githubapisample.R
import com.vivek.githubapisample.theme.GitHubApiSampleTheme
import com.vivek.githubapisample.theme.padding

/**
 * A composable function that displays a message when there is no network connection.
 *
 * @param modifier The modifier to apply to the composable.
 */
@Composable
fun NoNetwork(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Red)
            .padding(
                horizontal = MaterialTheme.padding.medium,
                vertical = MaterialTheme.padding.small
            )
    ) {
        Text(
            text = stringResource(R.string.no_internet_available),
            style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onPrimary)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoInternetPreview() {
    GitHubApiSampleTheme {
        NoNetwork()
    }
}