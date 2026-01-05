package com.example.newsapp.home.data

import androidx.paging.PagingData
import com.example.newsapp.app.persistance.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getTopHeadlines(
        category: String
    ): Flow<PagingData<CardNews>>
}