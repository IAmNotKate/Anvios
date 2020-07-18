package com.anvios.android.preferences

import android.content.Context
import com.anvios.android.BuildConfig
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

private const val APP_SETTINGS = "app_settings"
private const val LAST_RUN_CODE_VERSION_KEY = "last_code_version"
private const val DEVICE_TOKEN_KEY = "device_token"

class AppPreferences private constructor(context: Context) {
    private val preferences =
        context.applicationContext.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
    private val subject = BehaviorSubject.create<String>()

    init {
        getDeviceToken()?.let { subject.onNext(it) }
    }

    fun getLastRunCodeVersion(): Long =
        preferences.getLong(LAST_RUN_CODE_VERSION_KEY, NO_LAST_RUN_CODE_VERSION)

    fun saveCurrentCodeVersion() {
        saveCodeVersion(BuildConfig.VERSION_CODE.toLong())
    }

    fun saveDeviceToken(token: String) {
        if (token.isNotBlank()) {
            preferences.edit().putString(DEVICE_TOKEN_KEY, token).apply()
            subject.onNext(token)
        }
    }

    fun getDeviceToken(): String? = preferences.getString(DEVICE_TOKEN_KEY, null)

    fun getDeviceTokenObservable(): Observable<String> = subject

    private fun saveCodeVersion(codeVersion: Long) {
        if (codeVersion > 0) {
            preferences.edit().putLong(LAST_RUN_CODE_VERSION_KEY, codeVersion).apply()
        }
    }

    companion object {
        const val NO_LAST_RUN_CODE_VERSION: Long = -1

        @JvmStatic
        private var INSTANCE: AppPreferences? = null

        @JvmStatic
        fun getInstance(context: Context): AppPreferences {
            if (INSTANCE == null) INSTANCE = AppPreferences(context)
            return INSTANCE!!
        }
    }
}