package com.bininfo.domain

import com.bininfo.data.cache.BinInfoEntity

data class BinInfo(
    val bin: String,
    val brand: String,
    val bank: String,
    val bankSite: String,
    val bankPhone: String,
    val country: String,
    val lat: Int,
    val lon: Int
) {
    fun toDataBaseEntity(inputDate: String): BinInfoEntity {
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
