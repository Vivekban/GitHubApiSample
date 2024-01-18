package com.vivek.githubapisample.user.data

import com.vivek.githubapisample.common.data.AppException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RemoteUserRepositoryTest {

    private val userName = "vivek"
    private val user = User.fake()
    private val userDto = UserDto.fake()

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
        coEvery { userService.getUserInfo(userName) } returns Result.success(userDto)

        // Act
        val result = runBlocking { sut.fetchUserInfo(userName) }

        // Assert
        assertEquals(user, result.getOrNull())
    }

    @Test
    fun `fetchUserInfo() should return failure when user is not found`() {
        // Arrange
        coEvery { userService.getUserInfo(userName) } returns Result.failure(AppException.NotFound())

        // Act
        val result = runBlocking { sut.fetchUserInfo(userName) }

        // Assert
        assertEquals(AppException.NotFound(), result.exceptionOrNull())
    }

    @Test
    fun `fetchUserInfo() should return failure when there is a network error`() {
        // Arrange
        coEvery { userService.getUserInfo(userName) } returns Result.failure(AppException.NetworkError())

        // Act
        val result = runBlocking { sut.fetchUserInfo(userName) }

        // Assert
        assertEquals(AppException.NetworkError(), result.exceptionOrNull())
    }
}