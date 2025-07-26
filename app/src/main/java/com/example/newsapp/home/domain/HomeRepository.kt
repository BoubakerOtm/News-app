package com.example.newsapp.home.domain

import com.example.newsapp.app.network.NewsApi
import com.example.newsapp.app.network.Resource
import com.example.newsapp.home.data.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class HomeRepository(
    private val newsApi: NewsApi,
) {
    fun getTopHeadlines(): Flow<Resource<NewsResponse>> = flow {
        emit(Resource.Loading)
        try {
            val newsResponse = newsApi.getTopHeadlines()
            emit(Resource.Success(newsResponse))
        } catch (exception: Exception) {
            emit(Resource.Error(exception))
        }
    }


}