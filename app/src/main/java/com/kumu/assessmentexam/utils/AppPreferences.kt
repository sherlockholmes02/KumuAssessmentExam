package com.kumu.assessmentexam.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "KUMU_PREFS"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val SCREEN = Pair("SCREEN", 0)
    private val MEDIA = Pair("MEDIA", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var screen: Int
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getInt(SCREEN.first, SCREEN.second)!!
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putInt(SCREEN.first, value)
        }

    var media: String
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(MEDIA.first, MEDIA.second)!!
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(MEDIA.first, value)
        }
}