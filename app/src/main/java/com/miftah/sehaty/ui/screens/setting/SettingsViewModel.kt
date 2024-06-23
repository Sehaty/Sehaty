package com.miftah.sehaty.ui.screens.setting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miftah.sehaty.R
import com.miftah.sehaty.domain.usecase.app_entry.AccountIsActive
import com.miftah.sehaty.domain.usecase.app_entry.ActivatedAccount
import com.miftah.sehaty.domain.usecase.app_entry.GetSavedJWT
import com.miftah.sehaty.domain.usecase.scan.SaveScanningImageToCloud
import com.miftah.sehaty.domain.usecase.scan.SendScanningImage
import com.miftah.sehaty.domain.usecase.special.CheckSessionWA
import com.miftah.sehaty.domain.usecase.special.GetJWT
import com.miftah.sehaty.ui.screens.onboarding.OnBoardingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val accountIsActive: AccountIsActive,
    val activatedAccount: ActivatedAccount,
    val getSavedJWT: GetSavedJWT,
    val checkSessionWA: CheckSessionWA
) : ViewModel() {

    private val _settingsState = mutableStateOf(SettingsState())
    val settingsState: State<SettingsState>
        get() = _settingsState

    fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.ActivatedAccount -> {
                activeAccount()
            }

            is SettingsEvent.CheckWaSession -> {
                checkWa(event.jwt)
            }
        }
    }


    private fun getJWT() {
        _settingsState.value = _settingsState.value.copy(
            getSavedJWT = getSavedJWT()
        )
    }

    private fun isActive() {
        _settingsState.value = _settingsState.value.copy(
            isActive = accountIsActive()
        )
    }

    private fun setSettingItem() {
        val settingItem = listOf(
            SettingData(
                title = "WhatsApp",
                description = "Connect to WhatsApp",
                drawable = R.drawable.whatsapp_icon
            )
        )

        _settingsState.value = _settingsState.value.copy(
            itemSettings = settingItem
        )
    }

    private fun activeAccount() {
        viewModelScope.launch {
            activatedAccount()
        }
    }

    private fun checkWa(jwt : String) {
        _settingsState.value = _settingsState.value.copy(
            checkSessionWA = checkSessionWA(jwt)
        )
    }

    init {
        isActive()
        getJWT()
        setSettingItem()
    }
}