package com.vivek.githubapisample.repo.data

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Implementation of [RepoRemoteSource] uses retrofit to perform Network operation to
 * fetch repo related information
 */
interface RepoService : RepoRemoteSource {

    /**
     * Get a list of repositories for a given username
     *
     * @param username the username of the user
     * @param page the page number of the results
     * @param perPage the number of results per page
     * @return a response containing a list of repositories
     */
    @GET("users/{username}/repos")
    override suspend fun getRepositoryByUsername(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Result<List<RepoDto>>

    /**
     * Get a single repository by its owner and name
     *
     * @param owner the owner of the repository
     * @param name the name of the repository
     * @return a response containing the repository
     */
    @GET("repos/{owner}/{name}")
    override suspend fun getRepo(
        @Path("owner") owner: String,
        @Path("name") name: String
    ): Result<RepoDto>

    companion object Factory {
        /**
         * Create a new instance of [RepoService] using the given [Retrofit] instance
         *
         * @param retrofit the retrofit instance to use
         * @return a new instance of [RepoService]
         */
        fun create(retrofit: Retrofit): RepoService {
            return retrofit.create(RepoService::class.java)
        }
    }

}