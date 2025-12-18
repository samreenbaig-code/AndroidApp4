package com.example.androidapp4.data

import android.content.Context

object Prefs {
    private const val PREFS_NAME = "daily_affirm_prefs"
    private const val KEY_LAST_AFFIRMATION = "last_affirmation"

    fun saveLastAffirmation(context: Context, text: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LAST_AFFIRMATION, text)
            .apply()
    }

    fun loadLastAffirmation(context: Context): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_LAST_AFFIRMATION, null)
    }
}
