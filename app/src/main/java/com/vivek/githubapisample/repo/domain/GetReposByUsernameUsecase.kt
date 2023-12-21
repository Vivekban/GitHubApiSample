package com.vivek.githubapisample.repo.domain

import androidx.paging.PagingData
import com.vivek.githubapisample.common.domain.SuspendUsecase
import com.vivek.githubapisample.repo.data.Repo
import com.vivek.githubapisample.repo.domain.GetReposByUsernameUsecase.Param
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Usecase to get list of [Repo] by [Param.userName].
 *
 * @param repository to fetch information
 */
class GetReposByUsernameUsecase @Inject constructor(private val repository: RepoRepository) :
    SuspendUsecase<Param, Flow<PagingData<Repo>>> {

    /**
     * Parameters for the usecase.
     *
     * @property userName The username of the user to get repos for.
     */
    data class Param(val userName: String)

    override suspend fun invoke(params: Param): Flow<PagingData<Repo>> {
        return repository.getRepositoryByUsername(params.userName)
    }

}