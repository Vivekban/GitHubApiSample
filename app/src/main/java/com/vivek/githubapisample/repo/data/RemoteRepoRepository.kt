package com.vivek.githubapisample.repo.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vivek.githubapisample.api.AppException
import com.vivek.githubapisample.common.data.AppPagingSource
import com.vivek.githubapisample.common.data.AppResult
import com.vivek.githubapisample.common.data.DataConstant
import com.vivek.githubapisample.repo.domain.RepoRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Implementation of [RepoRepository] uses [RepoRemoteSource] to fetch Repo related information
 */
class RemoteRepoRepository @Inject constructor(private val remote: RepoRemoteSource) :
    RepoRepository {

    override suspend fun getRepositoryByUsername(
        username: String,
    ): Flow<PagingData<Repo>> {

        val pagingSourceFactory = {
            object : AppPagingSource<Repo>() {
                override suspend fun loadItems(page: Int, params: LoadParams<Int>): List<Repo> {
                    return remote.getRepositoryByUsername(username, page, params.loadSize)
                        .body()?.map { it.toModel() } ?: emptyList()
                }
            }
        }

        return Pager(
            initialKey = DataConstant.STARTING_PAGE_INDEX,
            config = PagingConfig(
                DataConstant.ITEMS_PER_PAGE,
                initialLoadSize = DataConstant.ITEMS_PER_PAGE,
                prefetchDistance = DataConstant.ITEMS_PREFETCH_DISTANCE
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    override suspend fun getRepo(name: String, owner: String): AppResult<Repo> {
        return try {
            val repo = remote.getRepo(owner, name)
            repo.body()?.let { sRepo -> AppResult.Success(sRepo.toModel()) }
                ?: AppResult.Error(AppException.NotFound())
        } catch (e: HttpException) {
            AppResult.Error(AppException.Unknown(e))
        } catch (e: IOException) {
            AppResult.Error(AppException.NoNetwork())
        }
    }

}