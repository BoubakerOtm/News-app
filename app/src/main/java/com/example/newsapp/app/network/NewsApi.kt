package com.example.newsapp.app.network

import com.example.newsapp.app.Constants
import com.example.newsapp.app.Constants.SEARCH_ENDPOINT
import com.example.newsapp.app.Constants.TOP_HEADLINES_ENDPOINT
import com.example.newsapp.home.data.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    
    @GET(TOP_HEADLINES_ENDPOINT)
    suspend fun getTopHeadlines(
        @Query(Constants.COUNTRY_PARAM) country: String = Constants.DEFAULT_COUNTRY,
        @Query(Constants.CATEGORY_PARAM) category: String = Constants.DEFAULT_CATEGORY,
        @Query(Constants.PAGE_SIZE_PARAM) pageSize: Int = Constants.DEFAULT_PAGE_SIZE,
        @Query(Constants.PAGE_PARAM) page: Int = Constants.DEFAULT_PAGE,
        @Query(Constants.API_KEY_PARAM) apiKey: String = Constants.API_KEY,
    ): NewsResponse

    @GET(SEARCH_ENDPOINT)
    suspend fun searchNews(
        @Query(Constants.QUERY_PARAM) query: String,
        @Query(Constants.PAGE_SIZE_PARAM) pageSize: Int = Constants.DEFAULT_PAGE_SIZE,
        @Query(Constants.PAGE_PARAM) page: Int = Constants.DEFAULT_PAGE,
        @Query(Constants.API_KEY_PARAM) apiKey: String = Constants.API_KEY,
    ): NewsResponse


}
