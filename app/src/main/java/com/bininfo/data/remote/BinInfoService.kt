package com.bininfo.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface BinInfoService {

    @GET("")
    suspend fun getBinInfo( @Query("") bin: String): BinInfoResponse
}