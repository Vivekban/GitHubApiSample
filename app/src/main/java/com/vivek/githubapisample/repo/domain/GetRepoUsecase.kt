package com.vivek.githubapisample.repo.domain

import com.vivek.githubapisample.common.domain.AsyncUsecase
import com.vivek.githubapisample.repo.data.Repo
import javax.inject.Inject

/**
 * Usecase to get a repository from the repository repository.
 *
 * @param repository to fetch information.
 */
class GetRepoUsecase @Inject constructor(private val repository: RepoRepository) :
    AsyncUsecase<GetRepoUsecase.Param, Result<Repo>> {

    /**
     * Parameters for the usecase.
     *
     * @property name The name of the repository.
     * @property owner The owner of the repository.
     */
    data class Param(val name: String, val owner: String)

    override suspend fun invoke(params: Param): Result<Repo> {
        return repository.getRepo(params.name, params.owner)
    }

}