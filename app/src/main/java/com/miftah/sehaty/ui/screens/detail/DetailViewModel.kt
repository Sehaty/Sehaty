package com.miftah.sehaty.ui.screens.detail

import android.content.Context
import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miftah.sehaty.domain.model.FoodAfterScan
import com.miftah.sehaty.domain.model.convertToFoodForCloud
import com.miftah.sehaty.domain.model.convertToFoodRequest
import com.miftah.sehaty.domain.model.convertToHistoryScan
import com.miftah.sehaty.domain.usecase.app_entry.AccountIsActive
import com.miftah.sehaty.domain.usecase.history.GetAllHistoryScanned
import com.miftah.sehaty.domain.usecase.history.SaveHistoryToDB
import com.miftah.sehaty.domain.usecase.scan.SaveScanningImageToCloud
import com.miftah.sehaty.domain.usecase.scan.SendScanningImage
import com.miftah.sehaty.ui.screens.history.SearchState
import com.miftah.sehaty.utils.reduceFileImage
import com.miftah.sehaty.utils.reduceFileImageCompat
import com.miftah.sehaty.utils.uriToFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val saveScanningImageToCloud: SaveScanningImageToCloud,
    val saveHistoryToDB: SaveHistoryToDB,
    val accountIsActive: AccountIsActive
) : ViewModel() {
    private var _detailState = mutableStateOf(DetailState())
    val detailState: State<DetailState> get() = _detailState

    private var contextRef: WeakReference<Context>? = null

    fun onEvent(event: DetailEvent) {
        when (event) {
            DetailEvent.SaveToCloud -> {
                sendScanningNutrientToCloud()
            }

            DetailEvent.SaveToLocal -> {
                sendScanningNutrientToDB()
            }
        }
    }

    fun setDetailState(foodAfterScan: FoodAfterScan) {
        _detailState.value = _detailState.value.copy(
            foodAfterScan = foodAfterScan
        )
        calculate()
    }

    private fun sendScanningNutrientToCloud() {
        _detailState.value.foodAfterScan?.convertToFoodForCloud()?.let {
            _detailState.value = _detailState.value.copy(
                saveFoodAfterScan = saveScanningImageToCloud(it)
            )
        }
    }

    private fun sendScanningNutrientToDB() {
        viewModelScope.launch {
            _detailState.value.foodAfterScan?.let {
                _detailState.value = _detailState.value.copy(
                    saveFoodAfterScan = saveHistoryToDB(it.convertToHistoryScan())
                )
            }
        }
    }

    private fun calculate() {
        _detailState.value.foodAfterScan?.let {
            val sodiumInGrams = it.sodium / 1000.0

            val total = it.totalCarbs + it.dietaryFiber + it.protein + sodiumInGrams + it.sugars + it.totalFat

            // Prevent division by zero
            if (total == 0.0) {
                _detailState.value = _detailState.value.copy(
                    dataNutrientPercentage = NutrientPercentage(
                        dietaryFiber = 0f,
                        totalCarbs = 0f,
                        sodium = 0f,
                        sugars = 0f,
                        protein = 0f,
                        totalFat = 0f
                    )
                )
            } else {
                _detailState.value = _detailState.value.copy(
                    dataNutrientPercentage = NutrientPercentage(
                        dietaryFiber = ((it.dietaryFiber / total) * 100).toFloat(),
                        totalCarbs = ((it.totalCarbs / total) * 100).toFloat(),
                        sodium = ((sodiumInGrams / total) * 100).toFloat(),
                        sugars = ((it.sugars / total) * 100).toFloat(),
                        protein = ((it.protein / total) * 100).toFloat(),
                        totalFat = ((it.totalFat / total) * 100).toFloat()
                    )
                )
            }
        }
    }

    private fun isAccountActive(){
        _detailState.value = _detailState.value.copy(
            isAccountActive = accountIsActive()
        )
    }


    init {
        isAccountActive()
    }
}