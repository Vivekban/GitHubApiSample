package com.vivek.githubapisample.repo.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.vivek.githubapisample.common.data.AppException
import com.vivek.githubapisample.common.data.AppResult
import com.vivek.githubapisample.repo.data.RemoteRepoRepository
import com.vivek.githubapisample.repo.data.Repo
import com.vivek.githubapisample.repo.domain.GetRepoUsecase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepoViewModelTest {
    private val repo = Repo.fake()

    private val name = "name"

    private val owner = "name"

    private lateinit var sut: RepoViewModel

    @MockK
    lateinit var savedStateHandle: SavedStateHandle

    @MockK(relaxed = true)
    lateinit var repository: RemoteRepoRepository

    lateinit var getRepoUsecase: GetRepoUsecase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)

        getRepoUsecase = GetRepoUsecase(repository)

        every { savedStateHandle.get<String>(any()) } returns name

        sut = RepoViewModel(getRepoUsecase, savedStateHandle)
    }

    @Test
    fun testViewModelInitialState() = runTest {
        val initialState = RepoUiState()

        assert(sut.state.value == initialState)
    }

    @Test
    fun stateEmitsLoadingState() = runTest {

        coEvery { repository.getRepo(name, owner) } returns Result.success(repo)

        assert(sut.state.value.repo == AppResult.Loading)

    }

    @Test
    fun emitsSuccessState() = runTest {

        // Arrange
        coEvery { repository.getRepo(name, owner) } returns Result.success(repo)

        // Assert
        sut.state.test {
            assert(awaitItem().repo == AppResult.Loading)
            assert(awaitItem().repo.getOrNull() == repo)
        }
    }

    @Test
    fun emitsErrorState() = runTest {

        // Arrange
        val expectedError = AppException.NotFound()
        coEvery { repository.getRepo(name, owner) } returns Result.failure<Repo>(expectedError)

        // Assert
        sut.state.test {
            assert(awaitItem().repo == AppResult.Loading)
            val result = awaitItem()
            assert(result.repo.exceptionOrNull() == expectedError)
        }
    }
}