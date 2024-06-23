package com.miftah.sehaty.ui.screens.setting

import com.miftah.sehaty.domain.usecase.special.CheckSessionWA
import com.miftah.sehaty.utils.UiState
import kotlinx.coroutines.flow.Flow

data class SettingsState(
    val getSavedJWT: Flow<String?>? = null,
    val isActive: Flow<Boolean>? = null,
    val itemSettings: List<SettingData> = listOf(),
    val checkSessionWA: Flow<UiState<String>>? = null
)
