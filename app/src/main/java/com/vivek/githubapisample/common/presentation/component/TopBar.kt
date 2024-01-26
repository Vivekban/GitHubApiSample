package com.vivek.githubapisample.common.presentation.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vivek.githubapisample.R
import com.vivek.githubapisample.theme.GitHubApiSampleTheme

/**
 * A composable function that creates a top app bar.
 *
 * @param modifier The modifier to apply to the top app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(@StringRes title: Int, modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier.statusBarsPadding(),
        title = {
            Text(text = stringResource(id = title))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    GitHubApiSampleTheme {
        TopBar(R.string.app_name)
    }
}