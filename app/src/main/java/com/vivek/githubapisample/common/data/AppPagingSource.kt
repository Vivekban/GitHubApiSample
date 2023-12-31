package com.vivek.githubapisample.common.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vivek.githubapisample.common.data.DataConstant.ITEMS_PER_PAGE
import com.vivek.githubapisample.common.data.DataConstant.STARTING_PAGE_INDEX
import okio.IOException
import retrofit2.HttpException

/**
 * Base class for paging source which handled common logic for previous and next page.
 *
 * Instead of [load] method child class should implement [loadItems] method which knows
 * which page to load.
 */
abstract class AppPagingSource<V : Any> : PagingSource<Int, V>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            // fetch items
            val items = loadItems(page, params)

            // determine next page/key
            val nextKey = when (items.size) {
                // all items are fetched so next key is null
                in 0 until params.loadSize -> null
                else -> {
                    // initial load size might be 3 * ITEMS_PER_PAGE
                    // ensure we're not requesting duplicating items, at the 2nd request
                    page + (params.loadSize / ITEMS_PER_PAGE.coerceAtMost(params.loadSize))
                }
            }
            LoadResult.Page(
                data = items,
                // null if first page otherwise page - 1
                prevKey = if (page <= STARTING_PAGE_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    /**
     * Load items based on page number and load params.
     *
     * @param page page number
     * @param params load params
     */
    abstract suspend fun loadItems(page: Int, params: LoadParams<Int>): List<V>

    override fun getRefreshKey(state: PagingState<Int, V>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}