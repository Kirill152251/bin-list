package com.bininfo.data.remote

import com.bininfo.domain.BinInfo

data class BinInfoResponse(
    val bank: Bank,
    val brand: String,
    val country: Country
) {
    fun toDomainModal(bin: String): BinInfo {
        return BinInfo(
            bin = bin,
            brand = brand,
            bank = bank.name,
            bankSite = bank.url,
            bankPhone = bank.phone,
            country = country.name,
            lat = country.latitude,
            lon = country.longitude
        )
    }
}