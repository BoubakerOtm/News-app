package com.example.newsapp.home.data

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.example.newsapp.BuildConfig
import com.example.newsapp.app.network.NewsApi
import com.example.newsapp.app.persistance.NewsDatabase
import com.example.newsapp.app.persistance.model.ArticleEntity
import com.example.newsapp.app.persistance.model.RemoteKeys
import com.example.newsapp.app.persistance.model.toEntity

@OptIn(ExperimentalPagingApi::class)
class TopHeadlinesRemoteMediator(
    private val api: NewsApi,
    private val db: NewsDatabase,
) : RemoteMediator<Int, ArticleEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            Log.d("Paging", "LoadType = $loadType, page = $page")
            val response = api.getTopHeadlines(
                page = page,
                pageSize = state.config.pageSize,
            )

            val endOfPaginationReached = response.articles.isEmpty()

            val prevPage = if (page == 1) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.articleDao().clearAll()
                    db.remoteKeysDao().clearRemoteKeys()
                }

                val keys = response.articles.map {
                    RemoteKeys(
                        articleUrl = it.url,
                        prevKey = prevPage,
                        nextKey = nextPage
                    )
                }

                db.remoteKeysDao().insertAll(keys)
                db.articleDao().insertAll(response.articles.map { it.toEntity() })
            }

            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ArticleEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { url ->
                db.remoteKeysDao().remoteKeysArticleUrl(url = url)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article ->
                db.remoteKeysDao().remoteKeysArticleUrl(url = article.url)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ArticleEntity>
    ): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article ->
                db.remoteKeysDao().remoteKeysArticleUrl(url = article.url)
            }
    }


}