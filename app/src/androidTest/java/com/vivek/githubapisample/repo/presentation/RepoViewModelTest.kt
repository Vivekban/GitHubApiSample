package com.vivek.githubapisample.repo.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.vivek.githubapisample.api.AppException
import com.vivek.githubapisample.common.data.AppResult
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

    private lateinit var sut: RepoViewModel

    @MockK
    lateinit var savedStateHandle: SavedStateHandle

    @MockK
    lateinit var getRepoUsecase: GetRepoUsecase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        every { savedStateHandle.get<String>(RepoViewModel.REPO_NANE_KEY) } returns "name"
        every { savedStateHandle.get<String>(RepoViewModel.REPO_OWNER_KEY) } returns "owner"

        sut = RepoViewModel(getRepoUsecase, savedStateHandle)

    }

    @Test
    fun testViewModelInitialState() = runTest {
        val initialState = RepoUiState()

        assert(sut.state.value == initialState)
    }

    @Test
    fun stateEmitsLoadingState() = runTest {

        coEvery { getRepoUsecase(any()) } returns AppResult.Loading


        assert(sut.state.value.repo == AppResult.Loading)
    }

    @Test
    fun emitsSuccessState() = runTest {

        // Arrange
        coEvery { getRepoUsecase(any()) } returns AppResult.Success(repo)

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
        coEvery { getRepoUsecase(any()) } returns AppResult.Error(expectedError)

        // Assert
        sut.state.test {
            assert(awaitItem().repo == AppResult.Loading)
            assert(awaitItem().repo.exceptionOrNull() == expectedError)
        }
    }
}