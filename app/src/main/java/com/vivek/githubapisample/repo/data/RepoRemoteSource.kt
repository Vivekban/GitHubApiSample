package com.vivek.githubapisample.repo.data

/**
 * Performs repo related actions like fetching user's repo or fetching detail of particular one.
 */
interface RepoRemoteSource {

    /**
     * Provide list of [Repo] based on [username]
     */

    suspend fun getRepositoryByUsername(
        username: String,
        page: Int,
        perPage: Int,
    ): Result<List<RepoDto>>

    /**
     * Provide repo detail based on [owner] and [name]
     *
     * @return response of [Repo]
     */
    suspend fun getRepo(owner: String, name: String): Result<RepoDto>

}