package com.example.newsapp.search.domain

import com.example.newsapp.app.network.NewsApi
import com.example.newsapp.home.data.NewsResponse

class SearchRepository(
    private val newsApi: NewsApi,
) {
    suspend fun searchNews(query: String): NewsResponse {
        return newsApi.searchNews(query)
    }


}