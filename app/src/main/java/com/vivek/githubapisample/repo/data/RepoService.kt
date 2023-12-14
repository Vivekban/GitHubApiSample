package com.vivek.githubapisample.repo.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Performs repository related actions like fetching user's repository
 */
interface RepoService {

    /**
     * Provide repositories based on [username]
     *
     * @return list of [Repo]
     */
    @GET("users/{username}/repos")
    suspend fun getRepositoryByUsername(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<List<Repo>>

    /**
     * Provide repo detail based on [owner] and [name]
     *
     * @return response of [Repo]
     */
    @GET("repos/{owner}/{name}")
    suspend fun getRepo(
        @Path("owner") owner: String,
        @Path("name") name: String
    ): Response<Repo>

    companion object {
        fun create(retrofit: Retrofit): RepoService {
            return retrofit.create(RepoService::class.java)
        }
    }

}