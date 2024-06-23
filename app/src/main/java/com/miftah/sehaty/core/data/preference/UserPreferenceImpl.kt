package com.miftah.sehaty.core.data.preference

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.miftah.sehaty.domain.preference.UserPreference
import com.miftah.sehaty.utils.Constant.APP_ENTRY
import com.miftah.sehaty.utils.Constant.IS_ACTIVE
import com.miftah.sehaty.utils.Constant.JWT_KEY
import com.miftah.sehaty.utils.Constant.SHARED_PREFERENCES_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SHARED_PREFERENCES_KEY)

private val readOnlyProperty = preferencesDataStore(name = SHARED_PREFERENCES_KEY)

val Context.dataStore: DataStore<Preferences> by readOnlyProperty

class UserPreferenceImpl @Inject constructor(private val application: Application) :
    UserPreference {

    override suspend fun saveJwt(jwt: String) {
        application.dataStore.edit { preferences ->
            preferences[JWT_KEY] = jwt
            preferences[APP_ENTRY] = true
        }
    }

    override fun getJwt(): Flow<String?> {
        return application.dataStore.data.map { preferences ->
            preferences[JWT_KEY]
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return application.dataStore.data.map { preferences ->
            preferences[APP_ENTRY] ?: false
        }
    }

    override fun accountIsActive(): Flow<Boolean> {
        return application.dataStore.data.map { preferences ->
            preferences[IS_ACTIVE] ?: false
        }
    }

    override suspend fun activatedAccount() {
        application.dataStore.edit { preferences ->
            preferences[IS_ACTIVE] = true
        }
    }
}