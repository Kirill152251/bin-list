package com.bininfo.data.cache

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BinInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(binInfo: BinInfoEntity)

    @Delete
    suspend fun delete(binInfo: BinInfoEntity)

    @Query("DELETE FROM BIN_DB")
    suspend fun deleteHistory()

    @Query("SELECT * FROM BIN_DB")
    fun getBinInfo(): Flow<List<BinInfoEntity>>
}