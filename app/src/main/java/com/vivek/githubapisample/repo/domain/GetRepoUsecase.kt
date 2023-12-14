package com.vivek.githubapisample.repo.domain

import com.vivek.githubapisample.common.SuspendUsecase
import com.vivek.githubapisample.repo.data.Repo
import javax.inject.Inject

class GetRepoUsecase @Inject constructor(private val repository: RepoRepository) :
    SuspendUsecase<GetRepoUsecase.Param, com.vivek.githubapisample.common.AppResult<Repo>> {

    data class Param(val name: String, val owner: String)

    override suspend fun invoke(params: Param): com.vivek.githubapisample.common.AppResult<Repo> {
        return repository.getRepo(params.name, params.owner)
    }

}