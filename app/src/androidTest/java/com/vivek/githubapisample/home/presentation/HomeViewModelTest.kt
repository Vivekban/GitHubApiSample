package com.vivek.githubapisample.home.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import app.cash.turbine.test
import com.vivek.githubapisample.MainCoroutineRule
import com.vivek.githubapisample.api.AppException
import com.vivek.githubapisample.common.data.AppResult
import com.vivek.githubapisample.common.presentation.helper.NetworkMonitor
import com.vivek.githubapisample.repo.data.Repo
import com.vivek.githubapisample.repo.domain.GetReposByUsernameUsecase
import com.vivek.githubapisample.runBlockingTest
import com.vivek.githubapisample.user.data.User
import com.vivek.githubapisample.user.domain.GetUserInfoUsecase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private val query = "test"
    private val user = User.fake()

    private lateinit var sut: HomeViewModel

    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    lateinit var getUserInfoUsecase: GetUserInfoUsecase

    @MockK
    lateinit var getReposByUsernameUsecase: GetReposByUsernameUsecase

    @MockK
    lateinit var networkMonitor: NetworkMonitor

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        coEvery {
            networkMonitor.isOnline
        } returns flowOf(true)

        savedStateHandle = SavedStateHandle().apply {
            set("user_name", null)
        }
        sut = HomeViewModel(
            getUserInfoUsecase,
            getReposByUsernameUsecase,
            savedStateHandle,
            networkMonitor
        )
    }

    @Test
    fun stateIsInitiallyDefault() = runTest {
        assertEquals(
            HomeUiState(),
            sut.state.value,
        )
    }

    @Test
    fun doSearchShouldUpdateSavedStateHandle() = coroutineRule.runBlockingTest {

        val uiAction = HomeUiAction.DoSearch(query)
        sut.handleUiAction(uiAction)
        delay(10)
        assertEquals(query, savedStateHandle[HomeViewModel.USER_NAME_KEY])
    }

    @Test
    fun doSearchShouldNotFetchUserOnInvalidQuery() = runTest {
        // Arrange
        coEvery { getUserInfoUsecase(any()) } returns AppResult.Success(User.fake())

        // Act
        sut.state.test {
            val uiAction = HomeUiAction.DoSearch("")
            sut.handleUiAction(uiAction)

            // Assert
            assertEquals(null, awaitItem().user?.getOrNull())

        }
    }

    @Test
    fun doSearchShouldFetchUserOnValidQuery() = runTest {
        // Arrange
        coEvery { getUserInfoUsecase(any()) } returns AppResult.Success(User.fake())

        // Act
        sut.state.test {
            val uiAction = HomeUiAction.DoSearch(query)
            sut.handleUiAction(uiAction)

            // Assert
            assertEquals(null, awaitItem().user?.getOrNull())
            assertEquals(AppResult.Loading, awaitItem().user)
            assertEquals(User.fake(), awaitItem().user?.getOrNull())
        }
    }

    @Test
    fun doSearchShouldReturnFailureIfNoUserFound() = runTest {
        // Arrange
        coEvery { getUserInfoUsecase(any()) } returns AppResult.Error(AppException.NotFound())

        // Act
        sut.state.test {
            val uiAction = HomeUiAction.DoSearch(query)
            sut.handleUiAction(uiAction)

            // Assert
            assertEquals(null, awaitItem().user?.getOrNull())
            assertEquals(AppResult.Loading, awaitItem().user)
            assertEquals(AppException.NotFound(), awaitItem().user?.exceptionOrNull())
        }
    }

    @Test
    fun updateUsernameSearchShouldUpdateUsernameSearchFlow() = runTest {
        val collectJob1 =
            launch(StandardTestDispatcher()) { sut.state.collect() }
        sut.handleUiAction(HomeUiAction.UpdateUsernameSearch(query))
        delay(10)
        assertEquals(query, sut.state.value.usernameQuery)

        collectJob1.cancel()
    }

    @Test
    fun reposByUserFlowShouldEmitPagingDataWhenUserIsValid() = runTest {

        val pagingData = PagingData.from(listOf(Repo.fake()))

        coEvery {
            getUserInfoUsecase(any())
        } returns AppResult.Success(user)

        coEvery {
            getReposByUsernameUsecase(any())
        } returns flowOf(pagingData)

        sut.reposByUserFlow.test {

            // Act
            sut.handleUiAction(HomeUiAction.DoSearch("test"))

            // Assert
            assertNotNull(awaitItem())
        }
    }
}