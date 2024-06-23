package com.miftah.sehaty.domain.preference

import kotlinx.coroutines.flow.Flow

interface UserPreference {
    suspend fun saveJwt(jwt: String)

    fun getJwt(): Flow<String?>

    fun readAppEntry(): Flow<Boolean>

    fun accountIsActive(): Flow<Boolean>

    suspend fun activatedAccount()
}