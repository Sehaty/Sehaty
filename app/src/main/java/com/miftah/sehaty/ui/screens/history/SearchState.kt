package com.miftah.sehaty.ui.screens.history

import androidx.paging.PagingData
import com.miftah.sehaty.core.data.local.entity.HistoryScannedEntity
import com.miftah.sehaty.domain.model.FoodAfterScan
import com.miftah.sehaty.domain.model.HistoryScanned
import com.miftah.sehaty.utils.UiState
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val scanHistory: Flow<PagingData<HistoryScanned>>? = null,
    val isAccountActive : Flow<Boolean>? = null
)
