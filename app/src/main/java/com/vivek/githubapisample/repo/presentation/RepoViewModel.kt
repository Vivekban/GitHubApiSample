package com.vivek.githubapisample.repo.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.githubapisample.common.AppResult
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
 * The ViewModel used in RepoDetail.
 */
@HiltViewModel
class RepoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRepoUsecase: GetRepoUsecase,
) : ViewModel() {

    private val _name: String = savedStateHandle.get<String>(REPO_NANE_KEY)!!

    private val _owner: String = savedStateHandle.get<String>(REPO_OWNER_KEY)!!

    private val _repoFlow =
        flow { emit(getRepoUsecase(GetRepoUsecase.Param(_name, _owner))) }.onStart {
            emit(AppResult.Loading)
        }

    /**
     * Expose [RepoUiState]
     */
    val state = _repoFlow.map { repo ->
        RepoUiState(repo = repo)
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = RepoUiState()
    )

    companion object {
        private const val REPO_NANE_KEY = "name"
        private const val REPO_OWNER_KEY = "owner"
    }

}

/**
 *  State of Repo Detail
 */
data class RepoUiState(
    val repo: AppResult<Repo> = AppResult.Loading
)