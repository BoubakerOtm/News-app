package com.example.newsapp.app.di

import com.example.newsapp.home.data.HomeRepository
import com.example.newsapp.home.domain.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun bindCameraRepository(
        impl: HomeRepositoryImpl,
    ): HomeRepository




}