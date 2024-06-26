package com.miftah.sehaty.domain.usecase.history

import android.icu.text.StringSearch
import androidx.paging.PagingData
import com.miftah.sehaty.core.data.local.entity.HistoryScannedEntity
import com.miftah.sehaty.domain.model.HistoryScanned
import com.miftah.sehaty.domain.repository.AppRepository
import com.miftah.sehaty.utils.UiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllHistoryScanned @Inject constructor(
    private val appRepository : AppRepository
) {
    operator fun invoke(isActive : Boolean): Flow<PagingData<HistoryScannedEntity>> {
        return appRepository.getAllHistory(isActive)
    }
}