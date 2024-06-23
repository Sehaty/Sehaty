package com.miftah.sehaty.ui.screens.setting

sealed class SettingsEvent {
    data object ActivatedAccount : SettingsEvent()

    data class CheckWaSession(val jwt: String) : SettingsEvent()
}