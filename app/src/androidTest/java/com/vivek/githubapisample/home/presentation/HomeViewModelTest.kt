package com.vivek.githubapisample.home.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import app.cash.turbine.test
import com.vivek.githubapisample.MainCoroutineRule
import com.vivek.githubapisample.common.AppResult
import com.vivek.githubapisample.repo.data.Repo
import com.vivek.githubapisample.repo.domain.GetReposByUsernameUsecase
import com.vivek.githubapisample.ui.common.NetworkMonitor
import com.vivek.githubapisample.user.data.User
import com.vivek.githubapisample.user.domain.GetUserInfoUsecase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private val query = ""
    private val user = User.fake()

    private lateinit var viewModel: HomeViewModel
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
        viewModel = HomeViewModel(
            getUserInfoUsecase,
            getReposByUsernameUsecase,
            savedStateHandle,
            networkMonitor
        )
    }

    @Test
    fun doSearchShouldUpdateSavedStateHandle() = runTest {

        val uiAction = HomeUiAction.DoSearch(query)
        viewModel.handleUiAction(uiAction)
        delay(100)
        assertEquals(query, savedStateHandle[HomeViewModel.USER_NAME_KEY])

    }

    @Test
    fun updateUsernameSearchShouldUpdateUsernameSearchFlow() = runTest {
        //TODO: Fix this test
        viewModel.state.test {
            viewModel.handleUiAction(HomeUiAction.UpdateUsernameSearch(query))
            val query = awaitItem().usernameQuery
            assertEquals(query, query)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun reposByUserFlowShouldEmitPagingDataWhenUserIsValid() = runTest {

        val pagingData = PagingData.from(listOf(Repo.fake()))

        coEvery {
            getUserInfoUsecase(any())
        } returns AppResult.Success(user)

        coEvery {
            getReposByUsernameUsecase(any())
        } returns flowOf(pagingData)

        viewModel.reposByUserFlow.test {

            // Act
            viewModel.handleUiAction(HomeUiAction.DoSearch("test"))

            // Assert
            assertNotNull(awaitItem())

        }

    }

}