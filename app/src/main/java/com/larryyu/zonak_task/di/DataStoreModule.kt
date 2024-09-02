package com.larryyu.zonak_task.di

import android.content.Context
import com.larryyu.zonak_task.data.datasource.PreferenceDataSource
import com.larryyu.zonak_task.data.repoimpl.PreferenceDataSourceImpl
import com.larryyu.zonak_task.data.repoimpl.PreferencesImplementation
import com.larryyu.zonak_task.domain.repo.PreferencesRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataSource(@ApplicationContext context: Context): PreferenceDataSource =
        PreferenceDataSourceImpl(context)

    @Provides
    @Singleton
    fun providePreferencesRepository(preferencesDataSource: PreferenceDataSource, moshi: Moshi): PreferencesRepository =
        PreferencesImplementation(preferencesDataSource, moshi)


}