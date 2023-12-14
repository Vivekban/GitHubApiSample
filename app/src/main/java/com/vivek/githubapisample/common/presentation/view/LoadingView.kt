package com.vivek.githubapisample.common.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vivek.githubapisample.theme.GitHubApiSampleTheme

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingViewPreview() {
    GitHubApiSampleTheme {
        LoadingView()
    }
}