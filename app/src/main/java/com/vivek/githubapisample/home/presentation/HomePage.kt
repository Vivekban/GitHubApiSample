package com.vivek.githubapisample.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.vivek.githubapisample.R
import com.vivek.githubapisample.common.AppResult
import com.vivek.githubapisample.repo.data.Repo
import com.vivek.githubapisample.repo.presentation.RepoListItem
import com.vivek.githubapisample.ui.common.ValueCallback
import com.vivek.githubapisample.ui.navigation.NavigationRoute
import com.vivek.githubapisample.ui.theme.GitHubApiSampleTheme
import com.vivek.githubapisample.ui.theme.padding
import com.vivek.githubapisample.user.data.User
import com.vivek.githubapisample.user.presentation.UserInfo
import kotlinx.coroutines.flow.flowOf
import java.util.Locale

/**
 * This route is used by Navigation Graph to show Splash Screen
 */
object HomeRoute : NavigationRoute("home")

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomePage(
    onRepoClick: ValueCallback<Repo>,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val state by homeViewModel.state.collectAsState()
    val repos = homeViewModel.reposByUserFlow.collectAsLazyPagingItems()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    HomePage(
        state = state,
        repos = repos,
        modifier = modifier,
        onUsernameChange = {
            homeViewModel.handleUiAction(HomeUiAction.UpdateUsernameSearch(it))
        },
        onSearchClick = {
            keyboardController?.hide()
            focusManager.clearFocus()
            homeViewModel.handleUiAction(HomeUiAction.DoSearch(it))
        },
        onRepoClick = onRepoClick
    )

}

@Composable
fun HomePage(
    state: HomeUiState,
    repos: LazyPagingItems<Repo>,
    modifier: Modifier = Modifier,
    onUsernameChange: ValueCallback<String>,
    onSearchClick: ValueCallback<String>,
    onRepoClick: ValueCallback<Repo>? = null,
) {
    val hostState = remember { SnackbarHostState() }

    val user = state.user?.getOrNull()

    // Show SnackBar on error
    val info = state.message?.getContent()?.let { stringResource(it) }
    LaunchedEffect(key1 = info) {
        info?.let {
            hostState.showSnackbar(
                message = it
            )
        }
    }

    Scaffold(modifier = modifier,
        snackbarHost = { SnackbarHost(hostState) },
        topBar = { TopBar(modifier = modifier) }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            AnimatedVisibility(visible = !state.isOnline) {
                NoNetwork()
            }
            Spacer(modifier = Modifier.height(MaterialTheme.padding.medium))
            UserNameField(
                modifier = Modifier.padding(horizontal = MaterialTheme.padding.medium),
                username = state.usernameQuery,
                onUsernameChange = onUsernameChange,
                onSearchClick = onSearchClick
            )
            Spacer(modifier = Modifier.height(MaterialTheme.padding.medium))
            AnimatedVisibility(
                visible = user != null,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { it / 2 },
                ),
            ) {
                user?.let {
                    UserInfo(
                        modifier = Modifier.padding(horizontal = MaterialTheme.padding.medium),
                        user = user
                    )
                }
            }
            AnimatedVisibility(
                visible = state.user?.isSuccess() == true,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { it / 2 },
                ),
                modifier = Modifier.weight(1f)
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(vertical = MaterialTheme.padding.small)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(MaterialTheme.padding.small))
                    }
                    items(repos.itemCount) { index ->
                        repos[index]?.let { repo ->
                            RepoListItem(
                                modifier = Modifier.padding(
                                    vertical = MaterialTheme.padding.small,
                                    horizontal = 14.dp
                                ),
                                repo = repo,
                                onClick = {
                                    onRepoClick?.invoke(repo)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier) {
    TopAppBar(
        modifier = modifier.statusBarsPadding(),
        title = {
            Text(text = stringResource(id = R.string.take_home))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

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

@Composable
fun UserNameField(
    username: String,
    onUsernameChange: ValueCallback<String>,
    onSearchClick: ValueCallback<String>,
    modifier: Modifier = Modifier
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            modifier = Modifier.weight(1f),
            value = username,
            onValueChange = onUsernameChange,
            label = { Text(text = stringResource(R.string.placeholder_user_id)) },
            colors = TextFieldDefaults.colors(
                focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                cursorColor = MaterialTheme.colorScheme.tertiary
            )
        )
        Spacer(modifier = Modifier.width(MaterialTheme.padding.medium))
        Button(
            enabled = username.isNotEmpty(),
            shape = MaterialTheme.shapes.extraSmall,
            onClick = {
                onSearchClick(username)
            }) {
            Text(text = stringResource(R.string.search).uppercase(Locale.ROOT))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserNameFieldPreview() {
    GitHubApiSampleTheme {
        UserNameField("1", onUsernameChange = {}, onSearchClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun NoInternetPreview() {
    GitHubApiSampleTheme {
        NoNetwork()
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    GitHubApiSampleTheme {
        HomePage(
            state = HomeUiState(user = AppResult.Success(User.fake())),
            repos = flowOf(
                PagingData.from(listOf(Repo.fake(), Repo.fake()))
            ).collectAsLazyPagingItems(),
            onUsernameChange = {},
            onSearchClick = {},
            onRepoClick = {},
        )
    }
}

