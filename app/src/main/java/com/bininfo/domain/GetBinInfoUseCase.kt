package com.bininfo.domain

import com.bininfo.data.remote.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetBinInfoUseCase {

    fun getBinInfo(bin: String): Flow<ApiResult<BinInfo>>

    class Base @Inject constructor(
        private val repository: BinInfoRepository
    ) : GetBinInfoUseCase {
        override fun getBinInfo(bin: String): Flow<ApiResult<BinInfo>> {
            return repository.getBinInfo(bin)
        }
    }
}