package com.bininfo.domain

import com.bininfo.data.remote.ApiResult
import kotlinx.coroutines.flow.Flow

interface BinInfoRepository {

    fun getBinInfo(bin: String) : Flow<ApiResult<BinInfo>>
}