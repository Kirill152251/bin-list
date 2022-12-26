package com.bininfo.domain

import kotlinx.coroutines.flow.Flow

interface CacheDataSource {

    suspend fun insertBinInfo(binInfo: BinInfoHistory)

    suspend fun deleteBinInfo(binInfo: BinInfoHistory)

    fun getAllBinInfo(): Flow<List<BinInfoHistory>>

    suspend fun deleteAllBinInfo()
}