package com.vivek.githubapisample.repo.domain

import androidx.paging.PagingData
import com.vivek.githubapisample.api.AppException
import com.vivek.githubapisample.common.AppResult
import com.vivek.githubapisample.repo.data.Repo
import kotlinx.coroutines.flow.Flow

/**
 * Manages
 */
interface RepoRepository {

    /**
     * Provide repositories based on [username]
     * If no repository found for give name then [AppResult.failure] with [AppException.NotFound]
     *
     *
     * @return list of [Repo]
     */
    suspend fun getRepositoryByUsername(
        username: String
    ): Flow<PagingData<Repo>>

    suspend fun getRepo(name: String, owner: String): AppResult<Repo>
}