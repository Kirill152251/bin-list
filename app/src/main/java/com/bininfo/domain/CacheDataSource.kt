package com.bininfo.domain

import kotlinx.coroutines.flow.Flow

interface CacheDataSource {

    fun insertBinInfo(binInfo: BinInfoHistory)

    fun deleteBinInfo(binInfo: BinInfoHistory)

    fun getAllBinInfo(): Flow<List<BinInfoHistory>>

    fun deleteAllBinInfo()
}