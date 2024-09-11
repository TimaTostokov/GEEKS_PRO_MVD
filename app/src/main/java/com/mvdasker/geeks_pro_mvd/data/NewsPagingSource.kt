package com.mvdasker.geeks_pro_mvd.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mvdasker.geeks_pro_mvd.data.remote.model.news.News
import com.mvdasker.geeks_pro_mvd.data.repositories.NewsRepository

class NewsPagingSource(
    private val repository: NewsRepository
) : PagingSource<Int, News>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val page = params.key ?: 1
            val pageSize = 20
            val data = repository.getPagingNews(page = page, pageSize = pageSize)
            val nextKey = if (data.results.isNullOrEmpty() || data.results.size < pageSize) {
                null
            } else {
                page + 1
            }
            val prevKey = if (page == 1) null else page - 1

            LoadResult.Page(
                data = data.results,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1) ?: state.closestPageToPosition(
                position
            )?.nextKey?.minus(1)
        }
    }
}