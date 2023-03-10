package com.bininfo.domain

import android.util.Log
import com.bininfo.data.remote.ApiResult
import com.bininfo.data.remote.BinInfoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

interface RemoteDataSource {

    fun fetchBinInfo(bin: String): Flow<ApiResult<BinInfo>>

    @Singleton
    class Base @Inject constructor(
        private val service: BinInfoService
    ) : RemoteDataSource {

        override fun fetchBinInfo(bin: String): Flow<ApiResult<BinInfo>> = flow {
            emit(ApiResult.Loading)
            try {
                val data = service.getBinInfo(bin).toDomainModal(bin)
                emit(ApiResult.Success(data))
            } catch (e: Exception) {
                emit(ApiResult.Error(e))
                Log.d("API_ERROR", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}