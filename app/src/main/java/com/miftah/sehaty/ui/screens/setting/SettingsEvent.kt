package com.miftah.sehaty.ui.screens.setting

sealed class SettingsEvent {
    data class ActivatedAccount(val phoneNumber: String) : SettingsEvent()

    data class CheckWaSession(val jwt: String) : SettingsEvent()
}