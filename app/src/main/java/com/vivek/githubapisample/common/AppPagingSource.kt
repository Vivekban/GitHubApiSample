package com.vivek.githubapisample.common

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vivek.githubapisample.common.DataConstant.ITEMS_PER_PAGE
import com.vivek.githubapisample.common.DataConstant.STARTING_PAGE_INDEX
import okio.IOException
import retrofit2.HttpException

/**
 * Base class for paging source which handled common logic for previous and next key.
 */
abstract class AppPagingSource<V : Any> : PagingSource<Int, V>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val items = loadItems(page, params)
            val nextKey = if (items.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                page + (params.loadSize / ITEMS_PER_PAGE)
            }
            LoadResult.Page(
                data = items,
                prevKey = if (page <= STARTING_PAGE_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    abstract suspend fun loadItems(page: Int, params: LoadParams<Int>): List<V>

    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}