package com.bininfo.di

import android.content.Context
import com.bininfo.data.cache.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        HistoryDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideCurrentWeatherDao(historyDatabase: HistoryDatabase) =
        historyDatabase.binInfoDao()
}