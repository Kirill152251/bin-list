package com.bininfo.domain

import com.bininfo.data.cache.BinInfoEntity

data class BinInfoHistory(
    val bin: String,
    val brand: String,
    val bank: String,
    val bankSite: String,
    val bankPhone: String,
    val country: String,
    val inputDate: String,
) {
    fun toDataBaseEntity(): BinInfoEntity {
        return BinInfoEntity(
            bin = bin,
            brand = brand,
            bank = bank,
            bankSite = bankSite,
            bankPhone = bankPhone,
            county = country,
            inputDate = inputDate
        )
    }
}
