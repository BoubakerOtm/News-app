package com.example.newsapp.app.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.app.persistance.dao.ArticleDao
import com.example.newsapp.app.persistance.dao.RemoteKeysDao
import com.example.newsapp.app.persistance.model.ArticleEntity
import com.example.newsapp.app.persistance.model.RemoteKeys

@Database(
    entities = [
        ArticleEntity::class,
        RemoteKeys::class
               ],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}