package com.vivek.githubapisample.repo.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vivek.githubapisample.R
import com.vivek.githubapisample.common.data.AppResult
import com.vivek.githubapisample.common.presentation.NavigationRoute
import com.vivek.githubapisample.common.presentation.VoidCallback
import com.vivek.githubapisample.common.presentation.helper.DateTimeUtils
import com.vivek.githubapisample.common.presentation.view.ErrorView
import com.vivek.githubapisample.common.presentation.view.LoadingView
import com.vivek.githubapisample.repo.data.Repo
import com.vivek.githubapisample.theme.GitHubApiSampleTheme
import com.vivek.githubapisample.theme.padding

/**
 * This route is used by Navigation Graph to show [RepoPage]
 */
object RepoRoute : NavigationRoute("repo")

/**
 * A composable function that displays the details of a repository.
 *
 * @param modifier The modifier to apply to the composable.
 * @param viewModel The view model for the repository.
 * @param onBackClick The callback to invoke when the user clicks the back button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoPage(
    modifier: Modifier = Modifier,
    viewModel: RepoViewModel = hiltViewModel(),
    onBackClick: VoidCallback? = null,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier, topBar = {
        TopAppBar(
            modifier = modifier.statusBarsPadding(),
            title = {
                Text(
                    text = stringResource(R.string.repo_detail)
                )
            },
            navigationIcon = {
                IconButton(onClick = { onBackClick?.invoke() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

    }) { contentPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = contentPadding.calculateTopPadding())
                .padding(MaterialTheme.padding.medium)
        ) {
            when (state.repo) {
                is AppResult.Loading -> LoadingView(modifier = Modifier.fillMaxSize())
                is AppResult.Error -> ErrorView(
                    title = stringResource(R.string.error_repo_fetch),
                    modifier = Modifier.fillMaxSize()
                )

                is AppResult.Success -> state.repo.getOrNull()?.let {
                    RepoDetail(
                        repo = state.repo.getOrNull()!!,
                    )
                }
            }
        }

    }
}

@Composable
fun RepoDetail(repo: Repo, modifier: Modifier = Modifier) {
    val showBadge = (repo.forks ?: 0) >= 5000
    val badgeId = "badge"
    val text = buildAnnotatedString {
        append(repo.name)
        if (showBadge) append(" ")
        if (showBadge) appendInlineContent(badgeId, "[icon]")
    }

    val inlineContent = mapOf(
        Pair(
            badgeId,
            InlineTextContent(
                Placeholder(
                    width = 16.sp,
                    height = 16.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Icon(Icons.Filled.Star, "", tint = Color.Red)
            }
        )
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val description = repo.displayDescription
        Spacer(modifier = Modifier.height(MaterialTheme.padding.medium))
        Text(
            text = text,
            inlineContent = inlineContent,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.Build,
                modifier = Modifier.size(MaterialTheme.padding.medium),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(MaterialTheme.padding.small))
            Text(
                text = (repo.forks ?: 0).toString(),
            )
            Spacer(modifier = Modifier.width(MaterialTheme.padding.medium))
            Icon(
                Icons.Default.DateRange,
                modifier = Modifier.size(MaterialTheme.padding.medium),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(MaterialTheme.padding.small))
            Text(
                text = DateTimeUtils.getDayWithMonthName(repo.updatedAt) ?: "",
            )
        }

        if (description.isNotEmpty()) Spacer(modifier = Modifier.height(MaterialTheme.padding.medium))

        if (description.isNotEmpty())
            Text(
                text = repo.displayDescription,
                style = MaterialTheme.typography.bodyLarge
            )

    }
}

@Preview(showBackground = true)
@Composable
fun RepoDetailPreview() {
    GitHubApiSampleTheme {
        RepoDetail(repo = Repo.fake())
    }
}