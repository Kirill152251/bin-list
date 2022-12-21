package com.bininfo.di

import com.bininfo.data.BaseBinInfoRepository
import com.bininfo.data.remote.RemoteDataSource
import com.bininfo.domain.BinInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface ModuleForInterfaces {

    @Binds
    fun provideBinInfoRepository(implementation: BaseBinInfoRepository): BinInfoRepository

    @Binds
    fun provideRemoteDataSource(implementation: RemoteDataSource.Base): RemoteDataSource
}