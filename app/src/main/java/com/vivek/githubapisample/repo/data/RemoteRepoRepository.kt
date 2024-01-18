package com.vivek.githubapisample.repo.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vivek.githubapisample.common.data.AppPagingSource
import com.vivek.githubapisample.common.data.DataConstant
import com.vivek.githubapisample.repo.domain.RepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of [RepoRepository] uses [RepoRemoteSource] to fetch Repo related information
 */
class RemoteRepoRepository @Inject constructor(
    private val remote: RepoRemoteSource,
    private val pagingConfig: PagingConfig
) :
    RepoRepository {

    override suspend fun getRepositoryByUsername(
        username: String,
    ): Flow<PagingData<Repo>> {

        // responsible for fetching data from remote source
        val pagingSourceFactory = {
            object : AppPagingSource<Repo>() {
                override suspend fun loadItems(
                    page: Int,
                    params: LoadParams<Int>
                ): Result<List<Repo>> {
                    // response comes as list of RepoDto to need to map it to Repo
                    return remote.getRepositoryByUsername(username, page, params.loadSize)
                        .map { list -> list.map { item -> item.toModel() } }
                }
            }
        }

        return Pager(
            initialKey = DataConstant.STARTING_PAGE_INDEX,
            config = pagingConfig,
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    override suspend fun getRepo(name: String, owner: String): Result<Repo> {
        // fetch and then convert it to Repo
        return remote.getRepo(owner, name).map { it.toModel() }
    }

}