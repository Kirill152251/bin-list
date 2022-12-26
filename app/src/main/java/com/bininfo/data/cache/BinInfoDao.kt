package com.bininfo.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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