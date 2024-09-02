package com.larryyu.zonak_task.di

import com.larryyu.zonak_task.data.remote.NewsEndPoint
import com.larryyu.zonak_task.data.repoimpl.NewsRepoImplementation
import com.larryyu.zonak_task.domain.repo.NewsRepo
import com.larryyu.zonak_task.ui.screens.home.base.HomeNewsReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Singleton
    @Provides
    fun provideNewsEndPoint(retrofit: Retrofit): NewsEndPoint =
        retrofit.create(NewsEndPoint::class.java)

    @Singleton
    @Provides
    fun provideNewsRepo(api: NewsEndPoint): NewsRepo =
        NewsRepoImplementation(api)

    @Singleton
    @Provides
    fun provideReducer() : HomeNewsReducer = HomeNewsReducer()
}