package com.bininfo.data

import com.bininfo.data.remote.ApiResult
import com.bininfo.domain.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseBinInfoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val cacheDataSource: CacheDataSource
): BinInfoRepository {

    override fun getBinInfo(bin: String): Flow<ApiResult<BinInfo>> {
        return remoteDataSource.fetchBinInfo(bin)
    }

    override suspend fun insertBinInfoIntoDb(binInfoHistory: BinInfoHistory) {
        cacheDataSource.insertBinInfo(binInfoHistory)
    }

    override fun deleteBinInfoFromDb(binInfoHistory: BinInfoHistory) {
        cacheDataSource.deleteBinInfo(binInfoHistory)
    }

    override fun getBinHistory(): Flow<List<BinInfoHistory>> {
        return cacheDataSource.getAllBinInfo()
    }

    override fun clearHistory() {
        cacheDataSource.deleteAllBinInfo()
    }
}