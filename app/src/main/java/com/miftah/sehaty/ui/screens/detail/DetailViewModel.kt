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
        var total = 0
        _detailState.value.foodAfterScan?.let {
            total = it.cholesterol
                ?: (0 + it.totalCarbs + it.dietaryFiber + it.protein + it.sodium + it.sugars)
            _detailState.value = _detailState.value.copy(
                dataNutrientPercentage = NutrientPercentage(
                    dietaryFiber = (it.dietaryFiber.toFloat()/total.toFloat())*100,
                    totalCarbs = (it.totalCarbs.toFloat()/total.toFloat())*100,
                    cholesterol = (it.cholesterol?.toFloat()?.div(total.toFloat()) ?: 0f)*100,
                    sodium = (it.sodium.toFloat()/total.toFloat())*100,
                    sugars = (it.sugars.toFloat()/total.toFloat())*100,
                    protein = (it.protein.toFloat()/total.toFloat())*100
                )
            )
        }
    }

    init {
        accountIsActive().onEach {
            _detailState.value = _detailState.value.copy(
                isActive = it
            )
        }
    }
}