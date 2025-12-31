package com.example.newsapp.app.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.BuildConfig
import com.example.newsapp.app.Constants
import com.example.newsapp.app.network.NewsApi
import com.example.newsapp.app.persistance.NewsDatabase
import com.example.newsapp.search.domain.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        val client = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor {
                val request = it.request().newBuilder()
                    .header("Authorization", BuildConfig.NEWS_API_KEY)
                    .build()
                it.proceed(request)
            }.build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNewDataBase(
        @ApplicationContext applicationContext: Context
    ): NewsDatabase {
        return Room.databaseBuilder(
            applicationContext,
            NewsDatabase::class.java,
            "news_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(newsDatabase: NewsDatabase) = newsDatabase.articleDao()

    @Provides
    @Singleton
    fun provideRemoteKeysDao(newsDatabase: NewsDatabase) = newsDatabase.remoteKeysDao()


    @Provides
    @Singleton
    fun provideSearchRepository(
        newsApi: NewsApi,
    ): SearchRepository {
        return SearchRepository(
            newsApi = newsApi,
        )
    }


}