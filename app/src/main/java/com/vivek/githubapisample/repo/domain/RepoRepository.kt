package com.vivek.githubapisample.repo.domain

import androidx.paging.PagingData
import com.vivek.githubapisample.common.AppResult
import com.vivek.githubapisample.repo.data.Repo
import kotlinx.coroutines.flow.Flow

/**
 * Manages repos related information
 */
interface RepoRepository {

    /**
     * Get a list of repositories for a given username.
     *
     * @param username The username of the user to get repositories for.
     *
     * @return A Flow of PagingData objects, each containing a list of repositories.
     */
    suspend fun getRepositoryByUsername(
        username: String
    ): Flow<PagingData<Repo>>

    /**
     * Get a single repository by name and owner.
     *
     * @param name The name of the repository.
     * @param owner The owner of the repository.
     *
     * @return The repository, or an error if it does not exist.
     */
    suspend fun getRepo(name: String, owner: String): AppResult<Repo>
}