package com.miftah.sehaty.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.miftah.sehaty.core.data.local.entity.HistoryScannedEntity
import com.miftah.sehaty.domain.model.FoodAfterScan
import com.miftah.sehaty.domain.model.FoodForCloud
import com.miftah.sehaty.domain.model.HistoryScanned
import com.miftah.sehaty.utils.UiState
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AppRepository {

    fun scanningNutrition(file: File): Flow<UiState<FoodAfterScan>>

    fun getAllHistory(isActive: Boolean): Flow<PagingData<HistoryScannedEntity>>

    fun getJWT(signKey: String): Flow<UiState<String>>

    fun getDetailHistoryByIndex(index: Int): Flow<UiState<HistoryScanned>>

    fun checkSessionWA(): Flow<UiState<String>>

    fun saveScanHistoryToCloud(foodForCloud: FoodForCloud): Flow<UiState<String>>

    suspend fun saveHistoryToDB(historyScanned: HistoryScanned): Flow<UiState<String>>
}