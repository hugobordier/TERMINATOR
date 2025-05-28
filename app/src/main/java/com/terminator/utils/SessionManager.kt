package com.terminator.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("fakeStorePrefs", Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        prefs.edit().putString("AUTH_TOKEN", token).apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString("AUTH_TOKEN", null)
    }

    fun clearToken() {
        prefs.edit().remove("AUTH_TOKEN").apply()
    }
}
