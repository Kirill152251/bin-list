package com.bininfo.di

import com.bininfo.data.BaseBinInfoRepository
import com.bininfo.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ModuleForInterfaces {

    @Singleton
    @Binds
    fun bindBinInfoRepository(impl: BaseBinInfoRepository): BinInfoRepository

    @Binds
    fun bindRemoteDataSource(impl: RemoteDataSource.Base): RemoteDataSource

    @Binds
    fun bindGetBinInfoUseCase(impl: GetBinInfoUseCase.Base): GetBinInfoUseCase

    @Binds
    fun bindInputValidationUseCase(impl: InputValidationUseCase.Base): InputValidationUseCase

    @Singleton
    @Binds
    fun bindResourceManager(impl: ManageResources.Base): ManageResources

    @Singleton
    @Binds
    fun bindCacheDataSource(impl: RemoteDataSource): CacheDataSource
}