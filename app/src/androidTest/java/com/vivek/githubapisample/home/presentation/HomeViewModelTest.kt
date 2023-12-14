package com.vivek.githubapisample.home.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.vivek.githubapisample.MainCoroutineRule
import com.vivek.githubapisample.repo.domain.GetReposByUsernameUsecase
import com.vivek.githubapisample.runBlockingTest
import com.vivek.githubapisample.ui.common.NetworkMonitor
import com.vivek.githubapisample.user.domain.GetUserInfoUsecase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@HiltAndroidTest
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val coroutineRule = MainCoroutineRule()

    @Inject
    lateinit var getUserInfoUsecase: GetUserInfoUsecase

    @Inject
    lateinit var getReposByUsernameUsecase: GetReposByUsernameUsecase

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)
        .around(coroutineRule)

    @Before
    fun setUp() {
        hiltRule.inject()
        val savedStateHandle: SavedStateHandle = SavedStateHandle().apply {
            set("user_name", "")
        }
        viewModel = HomeViewModel(
            getUserInfoUsecase,
            getReposByUsernameUsecase,
            savedStateHandle,
            networkMonitor
        )

    }

    @After
    fun tearDown() {
    }

    @Test
    @Throws(InterruptedException::class)
    fun testDefaultValues() = coroutineRule.runBlockingTest {
        assertEquals(HomeUiState(), viewModel.state.first())
    }

    @Test
    fun getReposByUserFlow() {
    }

    @Test
    fun getState() {
    }

    @Test
    fun handleUiAction() {
    }
}