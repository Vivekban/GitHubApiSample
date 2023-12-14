package com.vivek.githubapisample.common.data

/**
 * Contains various constants used in data layer
 */
object DataConstant {

    /**
     * Github cloud service base url
     */
    const val BASE_URL = "https://api.github.com/"

    const val STARTING_PAGE_INDEX = 1

    const val ITEMS_PER_PAGE = 20

    /**
     * Prefetch distance which defines how far from the edge of loaded content
     * an access must be to trigger further loading.
     */
    const val ITEMS_PREFETCH_DISTANCE = 10

}
