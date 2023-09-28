package com.prateek.makemy.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class MmtApp : Application() {

    private lateinit var prefs : SharedPreferences

    companion object{
        const val PREF_FILE_NAME = "prefs"
        const val KEY_USER_SIGNED_IN = "signed"
        const val KEY_USER_NAME = "username"
    }

    var userSignedIn : Boolean
        get() = prefs.getBoolean(KEY_USER_SIGNED_IN, false)
        set(value) = prefs
            .edit()
            .putBoolean(KEY_USER_SIGNED_IN, value)
            .apply()


    var username : String?
        get() = prefs.getString(KEY_USER_NAME, null)
        set(value) = prefs
            .edit()
            .putString(KEY_USER_NAME, value)
            .apply()


    override fun onCreate() {
        super.onCreate()
        prefs = applicationContext.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }
}