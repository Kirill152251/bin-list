package com.bininfo.domain

data class BinInfo(
    val bin: String,
    val brand: String,
    val bank: String,
    val bankSite: String,
    val bankPhone: String,
    val country: String,
    val lat: Int,
    val lon: Int
)
