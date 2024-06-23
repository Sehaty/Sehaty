package com.miftah.sehaty.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Constant {
    const val BASE_URL = "https://sehaty.akutegar.com/"

    const val WA_URL = "https://api.whatsapp.com/"

    const val APP_DATABASE = "APP_DATABASE"

    const val SHARED_PREFERENCES_KEY = "SHARED_PREFERENCES_KEY"

    const val FOOD_AFTER_SCAN = "FOOD_AFTER_SCAN"
    const val FOOD_URI = "FOOD_URI"

    val JWT_KEY = stringPreferencesKey("jwt")
    val APP_ENTRY = booleanPreferencesKey("AppEntry")
    val IS_ACTIVE = booleanPreferencesKey("isActive")

}