package com.bininfo.domain

import com.bininfo.data.remote.ApiResult
import kotlinx.coroutines.flow.Flow

interface BinInfoRepository {

    fun getBinInfo(bin: String) : Flow<ApiResult<BinInfo>>

    fun insertBinInfoIntoDb(binInfoHistory: BinInfoHistory)

    fun deleteBinInfoFromDb(binInfoHistory: BinInfoHistory)

    fun getBinHistory(): Flow<List<BinInfoHistory>>

    fun clearHistory()
}