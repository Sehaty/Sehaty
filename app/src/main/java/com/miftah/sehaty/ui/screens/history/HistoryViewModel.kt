package com.miftah.sehaty.ui.screens.history

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.miftah.sehaty.domain.model.convertToHistoryScanned
import com.miftah.sehaty.domain.usecase.app_entry.AccountIsActive
import com.miftah.sehaty.domain.usecase.history.GetAllHistoryScanned
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAllHistoryScanned: GetAllHistoryScanned,
    private val accountIsActive: AccountIsActive
) : ViewModel() {
    private var _state = mutableStateOf(SearchState())
    val state: State<SearchState> get() = _state

    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.GetAllHistory -> {
                searchHistory(event.isActive)
            }

            HistoryEvent.IsAccountActive -> {
                isAccountActive()
            }
        }
    }

    private fun isAccountActive(){
        _state.value = _state.value.copy(
            isAccountActive = accountIsActive()
        )
    }

    private fun searchHistory(isActive: Boolean) {
        _state.value = _state.value.copy(
            scanHistory = getAllHistoryScanned(
                isActive = isActive
            ).map { pagingData ->
                pagingData.map {
                    it.convertToHistoryScanned()
                }
            }.cachedIn(viewModelScope)
        )
    }

    init {
        isAccountActive()
    }
}