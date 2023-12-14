package com.vivek.githubapisample.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vivek.githubapisample.common.AppResult
import com.vivek.githubapisample.repo.data.Repo
import com.vivek.githubapisample.repo.domain.GetReposByUsernameUsecase
import com.vivek.githubapisample.ui.common.NetworkMonitor
import com.vivek.githubapisample.ui.common.OneTimeEvent
import com.vivek.githubapisample.ui.common.toUserFriendlyMessage
import com.vivek.githubapisample.user.data.User
import com.vivek.githubapisample.user.domain.GetUserInfoUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

/**
 * The ViewModel for the Home screen.
 *
 * This ViewModel is responsible for loading the user's information,
 * their repositories.
 *
 * @param getUserInfoUsecase The use case for getting user information.
 * @param getReposByUsernameUsecase The use case for getting a user's repositories.
 * @param savedStateHandle The SavedStateHandle for this ViewModel.
 * @param networkMonitor The NetworkMonitor for this ViewModel.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserInfoUsecase: GetUserInfoUsecase,
    private val getReposByUsernameUsecase: GetReposByUsernameUsecase,
    private val savedStateHandle: SavedStateHandle,
    networkMonitor: NetworkMonitor,
) :
    ViewModel() {

    companion object {
        /**
         * Key for last search username
         */
        private const val USER_NAME_KEY = "user_name"
    }

    /**
     * Contains the message to be shown to the user.
     */
    private val _messageFlow = MutableStateFlow<OneTimeEvent<Int>?>(null)

    /**
     * Contains the username to be searched. It will be updated once user press search button
     */
    private val _usernameFlow = savedStateHandle.getStateFlow(USER_NAME_KEY, "")

    /**
     * Contains ongoing text in username field, it will be updated once user type in username field
     */
    private val _usernameSearchFlow = MutableStateFlow(savedStateHandle[USER_NAME_KEY] ?: "")

    /**
     * Contains the user information based on [_usernameFlow], It will be called again as soon as
     * [_usernameFlow] updates.
     */
    private val _userFlow = _usernameFlow
        // check name is valid
        .filter { it.isNotEmpty() }
        // check if username is changed
        .distinctUntilChanged().map {
            getUserInfoUsecase(GetUserInfoUsecase.Param(it))
        }
        // emit loading state
        .onStart {
            emit(AppResult.Loading)
        }
        // listen for error and emit message if found any
        .onEach {
            it.exceptionOrNull()?.let { exception ->
                _messageFlow.emit(OneTimeEvent(exception.toUserFriendlyMessage()))
            }
        }

    /**
     * Contains the user repos information based on [_userFlow]
     * before searching it will sure valid user is there
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    val reposByUserFlow: Flow<PagingData<Repo>> = _userFlow
        // check if we have valid user
        .filterIsInstance(AppResult.Success::class).map { it.data }.filterIsInstance(User::class)
        .distinctUntilChanged()
        // fetch details
        .flatMapLatest {
            getReposByUsernameUsecase(
                (GetReposByUsernameUsecase.Param(
                    it.login ?: _usernameFlow.value
                ))
            )
        }.cachedIn(viewModelScope)

    /**
     * Tells if device is online or not
     */
    private val _isOnlineFlow =
        networkMonitor.isOnline.distinctUntilChanged().onStart { emit(true) }

    /**
     * The UI state for the Home screen.
     */
    private val _uiState =
        combine(
            _usernameSearchFlow,
            _userFlow,
            _messageFlow,
            _isOnlineFlow,
        ) { userName, user, message, isOnline ->
            HomeUiState(
                usernameQuery = userName,
                user = user,
                message = message,
                isOnline = isOnline
            )

        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = HomeUiState()
        )

    val state: StateFlow<HomeUiState> = _uiState

    /**
     * Handles a user action.
     *
     * @param uiAction The user action to handle.
     */
    fun handleUiAction(uiAction: HomeUiAction) {

        viewModelScope.launch {
            when (uiAction) {
                is HomeUiAction.DoSearch -> savedStateHandle[USER_NAME_KEY] = uiAction.query
                is HomeUiAction.UpdateUsernameSearch -> _usernameSearchFlow.emit(uiAction.query)
            }
        }
    }
}

/**
 * The UI actions for the Home screen.
 */
sealed interface HomeUiAction {
    /**
     * Call this when user performed search
     */
    data class DoSearch(val query: String) : HomeUiAction

    /**
     * Call this when user type in username field
     */
    data class UpdateUsernameSearch(val query: String) : HomeUiAction
}

@Immutable
data class HomeUiState(
    val usernameQuery: String = "",
    val user: AppResult<User>? = null,
    val isOnline: Boolean = true,
    val message: OneTimeEvent<Int>? = null
)