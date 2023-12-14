package com.vivek.githubapisample.repo.data

import com.vivek.githubapisample.api.AppException
import com.vivek.githubapisample.common.data.AppResult
import com.vivek.githubapisample.common.data.DataConstant
import com.vivek.githubapisample.fake.FakeResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemoteRepoRepositoryTest {

    private val username = "vivek"
    private val repoDto = RepoDto.fake()
    private val repo = Repo.fake()
    private val reposDto = listOf(repoDto)
    private val repoName = "1"
    private val owner = "vivek"

    @MockK
    private lateinit var service: RepoService

    private lateinit var sut: RemoteRepoRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = RemoteRepoRepository(service)
    }

    @Test
    fun `getRepositoryByUsername() should return a flow of PagingData`() {
        // Arrange

        coEvery {
            service.getRepositoryByUsername(
                username,
                1,
                DataConstant.ITEMS_PER_PAGE
            )
        } returns Response.success(reposDto)

        // Collect the flow
        runBlocking {
            // Act
            val result = sut.getRepositoryByUsername(username).first()

            // Assert
            assertNotNull(result)
        }
    }

    @Test
    fun `getRepositoryByUsername() should return an empty flow when there are no repos`() {
        // Arrange
        val username = "vivek"
        coEvery {
            service.getRepositoryByUsername(
                username,
                1,
                DataConstant.ITEMS_PER_PAGE
            )
        } returns Response.success(emptyList())

        // Collect the flow
        runBlocking {
            // Act
            val result = sut.getRepositoryByUsername(username).first()

            // Assert
            assertNotNull(result)
        }
    }

    @Test
    fun `getRepo() should return a success result when the repo is found`() {
        // Arrange

        coEvery { service.getRepo(owner, repoName) } returns Response.success(repoDto)

        // Act
        val result = runBlocking { sut.getRepo(repoName, owner) }

        // Assert
        assertTrue(result is AppResult.Success)
        assertEquals(repo, result.getOrNull())
    }

    @Test
    fun `getRepo() should return an error result when the repo is not found`() {
        // Arrange

        coEvery { service.getRepo(owner, repoName) } returns FakeResponse.notFound()
        // Act
        val result = runBlocking { sut.getRepo(repoName, owner) }

        // Assert
        assertEquals(AppException.NotFound(), result.exceptionOrNull())
    }
}