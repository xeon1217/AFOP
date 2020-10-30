package com.example.afop.data.dataSource

import android.content.Context
import android.content.SharedPreferences

/**
 * 각종 설정들을 제어할 수 있는 클래스
 */
class PreferenceManager {
    companion object {
        private lateinit var preferences: SharedPreferences
        private val PREFERENCE_PATH = "preferences"
        private val DEFAULT_VALUE_STRING: String = ""
        private val DEFAULT_VALUE_BOOLEAN: Boolean = false
        private val DEFAULT_VALUE_INT: Int = -1
        private val DEFAULT_VALUE_LONG: Long = -1L
        private val DEFAULT_VALUE_FLOAT: Float = -1F

        fun init(context: Context) {
            preferences = context.getSharedPreferences(PREFERENCE_PATH, Context.MODE_PRIVATE)
        }

        fun setString(key: String, value: String) {
            preferences.edit().putString(key, value).apply()
        }

        fun setBoolean(key: String, value: Boolean) {
            preferences.edit().putBoolean(key, value).apply()
        }

        fun setInt(key: String, value: Int) {
            preferences.edit().putInt(key, value).apply()
        }

        fun setLong(key: String, value: Long) {
            preferences.edit().putLong(key, value).apply()
        }

        fun setFloat(key: String, value: Float) {
            preferences.edit().putFloat(key, value).apply()
        }

        fun getString(key: String): String {
            return preferences.getString(key, DEFAULT_VALUE_STRING) as String
        }

        fun getBoolean(key: String): Boolean {
            return preferences.getBoolean(key, DEFAULT_VALUE_BOOLEAN)
        }

        fun getInt(key: String): Int {
            return preferences.getInt(key, DEFAULT_VALUE_INT)
        }

        fun getLong(key: String): Long {
            return preferences.getLong(key, DEFAULT_VALUE_LONG)
        }

        fun getFloat(key: String): Float {
            return preferences.getFloat(key, DEFAULT_VALUE_FLOAT)
        }

        fun removeKey(key: String) {
            preferences.edit().remove(key).apply()
        }

        fun clear() {
            preferences.edit().clear().apply()
        }
    }
}