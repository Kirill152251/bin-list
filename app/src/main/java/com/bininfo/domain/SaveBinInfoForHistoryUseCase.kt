package com.bininfo.domain

import javax.inject.Inject

interface SaveBinInfoForHistoryUseCase {

    suspend fun save(binInfoHistory: BinInfoHistory)

    class Base @Inject constructor(
        private val repository: BinInfoRepository
    ): SaveBinInfoForHistoryUseCase {
        override suspend fun save(binInfoHistory: BinInfoHistory) {
            repository.insertBinInfoIntoDb(binInfoHistory)
        }
    }
}