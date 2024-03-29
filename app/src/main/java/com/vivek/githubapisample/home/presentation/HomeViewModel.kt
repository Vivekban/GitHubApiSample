package com.vivek.githubapisample.home.presentation

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vivek.githubapisample.R
import com.vivek.githubapisample.common.data.AppResult
import com.vivek.githubapisample.common.data.asAppResultFlow
import com.vivek.githubapisample.common.presentation.OneTimeEvent
import com.vivek.githubapisample.common.presentation.UiString
import com.vivek.githubapisample.common.presentation.helper.NetworkMonitor
import com.vivek.githubapisample.common.presentation.helper.uiString
import com.vivek.githubapisample.common.presentation.uiString
import com.vivek.githubapisample.repo.data.Repo
import com.vivek.githubapisample.repo.domain.GetReposByUsernameUsecase
import com.vivek.githubapisample.user.data.User
import com.vivek.githubapisample.user.domain.GetUserInfoUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
) : ViewModel() {

    companion object {
        /** Key for last search username */
        const val USER_NAME_KEY = "user_name"
    }

    /**
     * Flow of the username to be searched. It will be updated once user press search button.
     * Used [savedStateHandle] for persisting username between abrupt crash.
     */
    private val _usernameFlow = savedStateHandle.getStateFlow(USER_NAME_KEY, "")

    /**
     * Flow of messages that need to be shown to the user. Mainly error messages
     * These events need to be handled one time so [OneTimeEvent] is used. This will ensure
     * error message will be shown only once.
     */
    private val _messageFlow = MutableStateFlow<OneTimeEvent<UiString>?>(null)

    /**
     * Contains ongoing text in username search field.
     * Initialized with savedStateHandle value
     */
    private val _searchFlow = MutableStateFlow(savedStateHandle[USER_NAME_KEY] ?: "")

    /**
     * Fetch the user information based on [_usernameFlow], It will be called again as soon as
     * [_usernameFlow] updates.
     */
    private val _userFlow = _usernameFlow
        // check name is valid
        .filter {
            // Added check for test cases
            try {
                it.isNotEmpty()
            } catch (e: NullPointerException) {
                false
            }
        }
        // check if username is changed
        .distinctUntilChanged()
        // fetch detail
        .map {
            getUserInfoUsecase(GetUserInfoUsecase.Param(it))
        }
        // convert to AppResult - it will help to handle exception and set initial state as loading.
        .asAppResultFlow()
        // listen for error and emit message
        .onEach {
            it.exceptionOrNull()?.let { exception ->
                _messageFlow.emit(OneTimeEvent(exception.uiString()))
            }
        }

    /**
     * Contains the user repos information based on [_userFlow]. It fetches repos automatically if
     * user name is changes and before searching it will ensure user is valid
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    val reposByUserFlow: Flow<PagingData<Repo>> = _userFlow
        // check if we have valid user
        .filterIsInstance(AppResult.Success::class)
        .map { it.data }
        .filterIsInstance(User::class)
        .distinctUntilChanged()
        // fetch repo details
        .flatMapLatest {
            getReposByUsernameUsecase(
                (GetReposByUsernameUsecase.Param(
                    it.login ?: _usernameFlow.value
                ))
            )
        }.catch {
            emit(PagingData.empty())
            _messageFlow.emit(OneTimeEvent(R.string.error_fetching_repositories.uiString))
        }
        .cachedIn(viewModelScope)

    /** Tells if device is online or not */
    private val _isOnlineFlow =
        networkMonitor.isOnline
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
            .catch {
                _messageFlow.emit(OneTimeEvent(R.string.error_fetching_network_status.uiString))
                emit(false)
            }
            .onStart { emit(true) }

    /**
     * The flow of [HomeUiState] which is computed from [_searchFlow], [_userFlow], [_messageFlow]
     * and [_isOnlineFlow].
     */
    private val _uiState =
        combine(
            _searchFlow,
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

    /** The flow of [HomeUiState] for the Home screen. */
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
                is HomeUiAction.UpdateUsernameSearch -> _searchFlow.emit(uiAction.query)
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
    val message: OneTimeEvent<UiString>? = null
)