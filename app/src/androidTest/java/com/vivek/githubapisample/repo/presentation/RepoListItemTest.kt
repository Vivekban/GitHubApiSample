package com.vivek.githubapisample.repo.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.vivek.githubapisample.repo.data.Repo
import org.junit.Rule
import org.junit.Test

class RepoListItemTest {

    private val repo = Repo.fake()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun repoListItemShouldDisplaysRepoNameAndDescription() {
        // Arrange
        composeTestRule.setContent {
            RepoListItem(repo)
        }

        // Assert
        composeTestRule.onNodeWithText(repo.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(repo.displayDescription).assertIsDisplayed()
    }

    @Test
    fun repoListItemShouldNotDisplaysDescriptionOnNoDescription() {
        // Arrange
        composeTestRule.setContent {
            RepoListItem(Repo.fake(description = ""))
        }

        // Assert
        composeTestRule.onNodeWithTag("description").assertDoesNotExist()
    }

    @Test
    fun repoListItemShouldBeClickable() {
        // Arrange
        var isClicked = false
        composeTestRule.setContent {
            RepoListItem(repo, onClick = {
                isClicked = true
            })
        }

        // Act
        composeTestRule.onNode(hasClickAction()).performClick()

        // Assert
        assert(isClicked)
    }
}