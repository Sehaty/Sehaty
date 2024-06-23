package com.miftah.sehaty.domain.usecase.app_entry

import com.miftah.sehaty.domain.preference.UserPreference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivatedAccount @Inject constructor(
    private val preference: UserPreference
) {
    suspend operator fun invoke() {
        preference.activatedAccount()
    }
}