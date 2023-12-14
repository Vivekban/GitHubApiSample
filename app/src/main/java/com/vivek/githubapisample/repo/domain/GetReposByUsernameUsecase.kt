package com.vivek.githubapisample.repo.domain

import androidx.paging.PagingData
import com.vivek.githubapisample.common.SuspendUsecase
import com.vivek.githubapisample.repo.data.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReposByUsernameUsecase @Inject constructor(private val repository: RepoRepository) :
    SuspendUsecase<GetReposByUsernameUsecase.Param, Flow<PagingData<Repo>>> {

    data class Param(val userName: String)

    override suspend fun invoke(params: Param): Flow<PagingData<Repo>> {
        return repository.getRepositoryByUsername(params.userName)
    }

}