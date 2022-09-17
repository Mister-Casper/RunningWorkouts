package com.sgcdeveloper.runwork.data.repository

import android.content.Context
import com.google.gson.Gson
import javax.inject.Inject


class SharedPreferencesStore @Inject constructor(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("runwork", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun <T> putValue(key: String, value: T) {
        when (value) {
            is String -> sharedPreferences.edit().putString(key, value).apply()
            is Int -> sharedPreferences.edit().putInt(key, value).apply()
            is Boolean -> sharedPreferences.edit().putBoolean(key, value).apply()
            is Float -> sharedPreferences.edit().putFloat(key, value).apply()
            is Long -> sharedPreferences.edit().putLong(key, value).apply()
            else  -> sharedPreferences.edit().putString(key, gson.toJson(value)).apply()
        }
    }

    fun <T> getValue(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    fun <T> getObject(key: String, defaultValue: T, type: Class<T>): T {
        return gson.fromJson(sharedPreferences.getString(key, ""), type) ?: defaultValue
    }
}