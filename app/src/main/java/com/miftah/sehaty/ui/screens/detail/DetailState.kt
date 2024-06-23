package com.miftah.sehaty.ui.screens.detail

import com.miftah.sehaty.domain.model.FoodAfterScan
import com.miftah.sehaty.utils.UiState
import kotlinx.coroutines.flow.Flow

data class DetailState (
    val foodAfterScan: FoodAfterScan? = null,
    val saveFoodAfterScan: Flow<UiState<String>>?  = null,
    val isActive : Boolean = false,
    val dataNutrientPercentage : NutrientPercentage? = NutrientPercentage()
)