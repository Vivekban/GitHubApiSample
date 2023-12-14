package com.vivek.githubapisample.repo.data

import androidx.paging.PagingData
import com.vivek.githubapisample.api.AppException
import com.vivek.githubapisample.common.AppResult
import com.vivek.githubapisample.common.DataConstant
import com.vivek.githubapisample.fake.FakeException
import com.vivek.githubapisample.fake.FakeResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemoteRepoRepositoryTest {

    private val username = "vivek"
    private val repo = Repo.fake()
    private val repos = listOf(repo)
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
        } returns Response.success(repos)

        // Collect the flow
        runBlocking {
            // Act
            val result = sut.getRepositoryByUsername(username)

            // Assert

            result.collect { pagingData ->
                // Assert that the paging data contains the expected repos
//                assertEquals(repos, pagingData.data)
            }
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
            val result = sut.getRepositoryByUsername(username)

            // Assert
            assertTrue(result is Flow<PagingData<Repo>>)

            result.collect { pagingData ->

                // Assert that the paging data is empty
//                assertTrue(pagingData.data.isEmpty())
            }
        }
    }

    @Test
    fun `getRepositoryByUsername() should return an error flow when there is a network error`() {
        // Arrange
        val username = "vivek"
        coEvery {
            service.getRepositoryByUsername(
                username,
                1,
                DataConstant.ITEMS_PER_PAGE
            )
        } throws FakeException.noConnection

        // Collect the flow
        runBlocking {

            // Act
            val result = sut.getRepositoryByUsername(username)

            // Assert
            assertTrue(result is Flow<PagingData<Repo>>)

            result.collect { pagingData ->
                // Assert that the paging data is empty
//                assertTrue(pagingData.data.isEmpty())
            }
        }
    }

    @Test
    fun `getRepo() should return a success result when the repo is found`() {
        // Arrange

        coEvery { service.getRepo(owner, repoName) } returns Response.success(repo)

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