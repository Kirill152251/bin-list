package com.bininfo.data

import com.bininfo.data.remote.ApiResult
import com.bininfo.data.remote.RemoteDataSource
import com.bininfo.domain.BinInfo
import com.bininfo.domain.BinInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseBinInfoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): BinInfoRepository {

    override fun getBinInfo(bin: String): Flow<ApiResult<BinInfo>> {
        return remoteDataSource.fetchBinInfo(bin)
    }
}