package com.vivek.githubapisample.repo.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vivek.githubapisample.repo.data.Repo
import com.vivek.githubapisample.common.presentation.VoidCallback
import com.vivek.githubapisample.theme.GitHubApiSampleTheme
import com.vivek.githubapisample.theme.padding

/**
 * A composable function that displays a single repository item in a list.
 *
 * @param repo The repository to display.
 * @param modifier The modifier to apply to the item.
 * @param onClick The callback to invoke when the item is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoListItem(
    repo: Repo,
    modifier: Modifier = Modifier,
    onClick: VoidCallback? = null
) {
    Card(
        onClick = { onClick?.invoke() },
        shape = MaterialTheme.shapes.extraSmall,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        val description = repo.displayDescription
        Column(Modifier.padding(MaterialTheme.padding.medium)) {
            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            )
            if (description.isNotEmpty()) Spacer(modifier = Modifier.height(MaterialTheme.padding.medium))

            if (description.isNotEmpty())
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.testTag("description")
                )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoListItemPreview() {
    GitHubApiSampleTheme {
        RepoListItem(Repo.fake())
    }
}
