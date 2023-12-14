package com.vivek.githubapisample.repo.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vivek.githubapisample.api.AppException
import com.vivek.githubapisample.common.AppPagingSource
import com.vivek.githubapisample.common.AppResult
import com.vivek.githubapisample.common.DataConstant
import com.vivek.githubapisample.repo.domain.RepoRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Implementation of UserService uses remote source to fetch User information
 */
class RemoteRepoRepository @Inject constructor(private val service: RepoService) :
    RepoRepository {

    override suspend fun getRepositoryByUsername(
        username: String,
    ): Flow<PagingData<Repo>> {

        val pagingSourceFactory = {
            object : AppPagingSource<Repo>() {
                override suspend fun loadItems(page: Int, params: LoadParams<Int>): List<Repo> {
                    return service.getRepositoryByUsername(username, page, params.loadSize)
                        .body() ?: emptyList()
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
            val repo = service.getRepo(owner, name)
            repo.body()?.let { sUser -> AppResult.Success(sUser) }
                ?: AppResult.Error(AppException.Unknown())
        } catch (e: HttpException) {
            AppResult.Error(AppException.NotFound(e))
        } catch (e: IOException) {
            AppResult.Error(AppException.NoNetwork())
        }
    }

}