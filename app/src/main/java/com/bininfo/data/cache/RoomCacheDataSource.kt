package com.bininfo.data.cache

import com.bininfo.domain.BinInfoHistory
import com.bininfo.domain.CacheDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomCacheDataSource @Inject constructor(
    private val dao: BinInfoDao
) : CacheDataSource {

    override suspend fun insertBinInfo(binInfo: BinInfoHistory) {
        dao.insert(binInfo.toDataBaseEntity())
    }

    override suspend fun deleteBinInfo(binInfo: BinInfoHistory) {
        dao.delete(binInfo.toDataBaseEntity())
    }

    override fun getAllBinInfo(): Flow<List<BinInfoHistory>> {
        return dao.getBinInfo().map { list ->
            list.map { entity ->
                entity.toBinInfoHistoryModel()
            }
        }
    }

    override suspend fun deleteAllBinInfo() {
        dao.deleteHistory()
    }
}