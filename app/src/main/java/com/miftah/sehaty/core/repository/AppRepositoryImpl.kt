package com.miftah.sehaty.core.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.log
import com.miftah.sehaty.core.data.local.entity.HistoryScannedEntity
import com.miftah.sehaty.core.data.local.room.AppDatabase
import com.miftah.sehaty.core.data.remote.dto.request.SignKeyRequest
import com.miftah.sehaty.core.data.remote.retrofit.ApiService
import com.miftah.sehaty.core.remote_mediator.HistoryScannedRemoteMediator
import com.miftah.sehaty.domain.model.FoodAfterScan
import com.miftah.sehaty.domain.model.FoodForCloud
import com.miftah.sehaty.domain.model.HistoryScanned
import com.miftah.sehaty.domain.model.convertToFoodAfterScan
import com.miftah.sehaty.domain.model.convertToFoodRequest
import com.miftah.sehaty.domain.model.convertToHistoryEntity
import com.miftah.sehaty.domain.model.convertToHistoryScanned
import com.miftah.sehaty.domain.model.getHistoryConvertToScanItem
import com.miftah.sehaty.domain.repository.AppRepository
import com.miftah.sehaty.utils.AppUtility.parseNumber
import com.miftah.sehaty.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : AppRepository {
    override fun scanningNutrition(file: File): Flow<UiState<FoodAfterScan>> =
        flow {
            emit(UiState.Loading)
            try {
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val photoPart = MultipartBody.Part.createFormData("image", file.name, requestFile)
                val result = apiService.scanNutrition(photoPart)
                if (result.message == "error") {
                    emit(UiState.Error(result.message))
                } else {
                    emit(UiState.Success(result.convertToFoodAfterScan()))
                }
            } catch (e: HttpException) {
                emit(UiState.Error(e.message.toString()))
            }
        }

    override suspend fun saveHistoryToDB(historyScanned: HistoryScanned): Flow<UiState<String>> = flow {
        emit(UiState.Loading)
        try {
            appDatabase.historyScannedDao().insertHistoryScanned(historyScanned.convertToHistoryEntity())
            emit(UiState.Success("Scc"))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Err"))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllHistory(
        search: String,
        isActive: Boolean
    ): Flow<PagingData<HistoryScannedEntity>> {
        Log.d("TAG", "getAllHistory: ")
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = HistoryScannedRemoteMediator(
                apiService,
                appDatabase,
                search,
                isActive
            ),
            pagingSourceFactory = {
                appDatabase.historyScannedDao().getAllHistoryScanned()
            }
        ).flow
    }

    override fun getJWT(signKey: String): Flow<UiState<String>> = flow {
        emit(UiState.Loading)
        try {
            val result = apiService.generateJWT(SignKeyRequest(signKey))
            if (result.accessCode == null) {
                emit(UiState.Error(result.message))
            } else {
                emit(UiState.Success(result.accessCode))
            }
        } catch (e: HttpException) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    override fun getDetailHistoryByIndex(index: Int): Flow<UiState<HistoryScanned>> = flow {
        emit(UiState.Loading)
        try {
            val result = appDatabase.historyScannedDao().findHistoryScanItemWithID(index)
            emit(UiState.Success(result.convertToHistoryScanned()))
        } catch (e: HttpException) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    override fun checkSessionWA(): Flow<UiState<String>> = flow {
        emit(UiState.Loading)
        try {
            val result = apiService.checkSession()
            if (result.userNumber == null) {
                emit(UiState.Error(result.message))
            } else {
                emit(UiState.Success(parseNumber(result.userNumber)))
            }
        } catch (e: HttpException) {
            emit(UiState.Error(e.message.toString()))
        }
    }

    override fun saveScanHistoryToCloud(foodForCloud: FoodForCloud): Flow<UiState<String>> = flow {
        emit(UiState.Loading)
        try {
            /*val (name, nutrition, photo) = foodForCloud.convertToFoodRequest()
            val result = apiService.addHistory(name, photo, nutrition)*/
            val result = apiService.addHistory(foodForCloud.convertToFoodRequest())
            if (result.error != null) {
                emit(UiState.Error(result.message))
            } else {
                emit(UiState.Success(result.status!!))
            }
        } catch (e: HttpException) {
            emit(UiState.Error(e.message.toString()))
        }
    }
}