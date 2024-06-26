package com.miftah.sehaty.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AppUtility {
    @TypeConverter
    fun fromStringToList(value: String?): List<String> {
        return if (!value.isNullOrBlank()) {
            val listType = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(value, listType)
        } else {
            emptyList()
        }
    }

    @TypeConverter
    fun fromListToString(list: List<String>?): String {
        return if(!list.isNullOrEmpty()) {
            Gson().toJson(list)
        } else {
            ""
        }
    }

    fun getRandomString(length: Int) : String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    fun parseNumber(input : String) : String {
        val parts = input.split("@")
        val number = parts[0]
        return number
    }
}