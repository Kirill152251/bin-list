package com.bininfo.domain

import com.bininfo.data.remote.ApiResult
import kotlinx.coroutines.flow.Flow

interface BinInfoRepository {

    fun getBinInfo(bin: String): Flow<ApiResult<BinInfo>>

    suspend fun insertBinInfoIntoDb(binInfoHistory: BinInfoHistory)

    suspend fun deleteBinInfoFromDb(binInfoHistory: BinInfoHistory)

    fun getBinHistory(): Flow<List<BinInfoHistory>>

    suspend fun clearHistory()
}