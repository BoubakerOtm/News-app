package com.example.newsapp.home.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.newsapp.app.network.NewsApi
import com.example.newsapp.app.persistance.NewsDatabase
import com.example.newsapp.app.persistance.model.toCardNews
import com.example.newsapp.home.data.HomeRepository
import com.example.newsapp.home.data.TopHeadlinesRemoteMediator
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val db: NewsDatabase,
) : HomeRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getTopHeadlines(category: String?) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
//                prefetchDistance = 2,
                enablePlaceholders = false,
            ),
            remoteMediator = TopHeadlinesRemoteMediator(
                api = api,
                db = db,
            ),
            pagingSourceFactory = {
                db.articleDao().pagingSource()
            }
        ).flow.map { pagingData ->
            pagingData.map { articleEntity ->
                articleEntity.toCardNews()
            }
        }

}