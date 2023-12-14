package com.vivek.githubapisample.repo.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Implementation of [RepoRemoteSource] uses retrofit to perform Network operation to
 * fetch repo related information
 */
interface RepoService : RepoRemoteSource {

    @GET("users/{username}/repos")
    override suspend fun getRepositoryByUsername(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<List<RepoDto>>

    @GET("repos/{owner}/{name}")
    override suspend fun getRepo(
        @Path("owner") owner: String,
        @Path("name") name: String
    ): Response<RepoDto>

    companion object {
        fun create(retrofit: Retrofit): RepoService {
            return retrofit.create(RepoService::class.java)
        }
    }

}