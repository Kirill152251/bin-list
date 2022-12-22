package com.bininfo.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface BinInfoService {

    @GET("/{bin}")
    suspend fun getBinInfo( @Path("bin") bin: String): BinInfoResponse
}