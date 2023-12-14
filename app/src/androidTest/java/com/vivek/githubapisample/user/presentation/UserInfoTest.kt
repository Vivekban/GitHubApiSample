package com.vivek.githubapisample.user.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.vivek.githubapisample.user.data.User
import org.junit.Rule
import org.junit.Test

class UserInfoTest {

    private val user = User.fake()

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun userInfoShouldDisplaysAvatarAndDescription() {

        // Arrange
        composeTestRule.setContent {
            UserInfo(user)
        }

        // Assert
        composeTestRule.onNodeWithText(user.displayName).assertIsDisplayed()
        composeTestRule.onNodeWithTag("image").assertIsDisplayed()
    }

}