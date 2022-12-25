package com.bininfo.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bininfo.domain.BIN_INFO_DATABASE

@Database(entities = [BinInfoEntity::class], version = 1)
abstract class HistoryDatabase: RoomDatabase() {

    abstract fun binInfoDao(): BinInfoDao

    companion object {
        @Volatile
        private var instance: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, HistoryDatabase::class.java, BIN_INFO_DATABASE)
                .fallbackToDestructiveMigration()
                .build()
    }
}