package com.miftah.sehaty.ui.screens.history

sealed class HistoryEvent {

    data class GetAllHistory(val isActive : Boolean) : HistoryEvent()

    data object IsAccountActive : HistoryEvent()
}