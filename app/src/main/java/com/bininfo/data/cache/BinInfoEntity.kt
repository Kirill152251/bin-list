package com.bininfo.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bininfo.domain.BIN_INFO_DATABASE
import com.bininfo.domain.BinInfoHistory

@Entity(tableName = BIN_INFO_DATABASE)
data class BinInfoEntity(
    @PrimaryKey(autoGenerate = false)
    val inputDate: String,
    val bin: String,
    val brand: String,
    val bank: String,
    val bankSite: String,
    val bankPhone: String,
    val county: String,
) {
    fun toBinInfoHistoryModel(): BinInfoHistory {
        return BinInfoHistory(
            bin = bin,
            brand = brand,
            bank = bank,
            bankSite = bankSite,
            bankPhone = bankPhone,
            country = county,
            inputDate = inputDate
        )
    }
}