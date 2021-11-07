package com.peter.webkeysnews.pojo.datasource

import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagingSource
import com.peter.webkeysnews.network.ApiService
import com.peter.webkeysnews.pojo.Article

class PostDataSource(private val apiService: ApiService) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getResponse(currentLoadingPageKey,"apple",
                "YOUR_AOI_KEY" )
            val responseData = mutableListOf<Article>()
            val data = response.body()?.articles ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}