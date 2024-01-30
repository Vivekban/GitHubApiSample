package com.vivek.githubapisample.user.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vivek.githubapisample.R
import com.vivek.githubapisample.theme.GitHubApiSampleTheme
import com.vivek.githubapisample.theme.padding
import com.vivek.githubapisample.user.data.User

/**
 * A composable function that displays user information.
 *
 * @param user The user to display information for.
 * @param modifier The modifier to apply to the composable.
 */
@Composable
fun UserInfo(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.label_user_image),
            modifier = Modifier
                .size(120.dp)
                .testTag("image")
        )
        Spacer(modifier = Modifier.height(MaterialTheme.padding.small))
        Text(
            text = user.displayName,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    GitHubApiSampleTheme {
        UserInfo(User.fake())
    }
}