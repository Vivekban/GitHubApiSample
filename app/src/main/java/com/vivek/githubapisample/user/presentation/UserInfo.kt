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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vivek.githubapisample.home.presentation.HomeRoute
import com.vivek.githubapisample.ui.theme.GitHubApiSampleTheme
import com.vivek.githubapisample.ui.theme.padding
import com.vivek.githubapisample.user.data.User

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
            contentDescription = HomeRoute.title,
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