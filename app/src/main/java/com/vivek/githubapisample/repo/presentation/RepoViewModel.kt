package com.vivek.githubapisample.repo.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.githubapisample.common.data.AppResult
import com.vivek.githubapisample.repo.data.Repo
import com.vivek.githubapisample.repo.domain.GetRepoUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * A ViewModel for the repository details screen.
 *
 * The ViewModel is initialized with the name and owner of the repository as arguments.
 * These arguments are passed to the [GetRepoUsecase] to fetch the repository details.
 */
@HiltViewModel
class RepoViewModel @Inject constructor(
    private val getRepoUsecase: GetRepoUsecase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _arguments = RepoArgs(savedStateHandle)

    private val _repoFlow =
        flow {
            emit(
                getRepoUsecase(
                    GetRepoUsecase.Param(
                        _arguments.name,
                        _arguments.owner
                    )
                )
            )
        }.onStart {
            emit(AppResult.Loading)
        }

    /**
     * Expose [RepoUiState] which basically contains information related to the repo.
     *
     * It remains active for [5000ms] after  disappearance of the last subscriber
     */
    val state = _repoFlow.map { repo ->
        RepoUiState(repo = repo)
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = RepoUiState()
    )

}

/**
 *  State of Repo Detail contains information of a repo.
 */
data class RepoUiState(
    /**
     * Contains detail of a repo.
     */
    val repo: AppResult<Repo> = AppResult.Loading
)