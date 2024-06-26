package com.miftah.sehaty.domain.usecase.scan

import com.miftah.sehaty.domain.model.FoodAfterScan
import com.miftah.sehaty.domain.model.HistoryScanned
import com.miftah.sehaty.domain.repository.AppRepository
import com.miftah.sehaty.utils.UiState
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class SendScanningImage @Inject constructor(
    private val appRepository: AppRepository
) {
    operator fun invoke(file: File): Flow<UiState<FoodAfterScan>> {
        return appRepository.scanningNutrition(file)
    }
}