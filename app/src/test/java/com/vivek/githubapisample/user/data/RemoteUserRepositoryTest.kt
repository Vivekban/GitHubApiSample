package com.vivek.githubapisample.user.data

import com.vivek.githubapisample.api.AppException
import com.vivek.githubapisample.fake.FakeException
import com.vivek.githubapisample.fake.FakeResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class RemoteUserRepositoryTest {

    private val userName = "vivek"
    private val user = User.fake()

    @MockK
    private lateinit var userService: UserService

    private lateinit var sut: RemoteUserRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = RemoteUserRepository(userService)
    }

    @Test
    fun `fetchUserInfo() should return success when user is found`() {
        // Arrange
        coEvery { userService.getUserInfo(userName) } returns Response.success(user)

        // Act
        val result = runBlocking { sut.fetchUserInfo(userName) }

        // Assert
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `fetchUserInfo() should return failure when user is not found`() {
        // Arrange
        coEvery { userService.getUserInfo(userName) } returns FakeResponse.notFound()

        // Act
        val result = runBlocking { sut.fetchUserInfo(userName) }

        // Assert
        assertEquals(AppException.NotFound(), result.exceptionOrNull())
    }

    @Test
    fun `fetchUserInfo() should return failure when there is a network error`() {
        // Arrange
        coEvery { userService.getUserInfo(userName) } throws FakeException.noConnection

        // Act
        val result = runBlocking { sut.fetchUserInfo(userName) }

        // Assert
        assertEquals(AppException.Unknown(), result.exceptionOrNull())
    }
}