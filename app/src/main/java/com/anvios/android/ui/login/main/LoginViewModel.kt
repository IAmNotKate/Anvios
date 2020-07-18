package com.anvios.android.ui.login.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.anvios.android.preferences.AppPreferences
import io.reactivex.Observable

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = AppPreferences.getInstance(application.applicationContext)

    fun getNavigationObservable(): Observable<Boolean> =
        preferences.getDeviceTokenObservable().map { it.isNotEmpty() }.distinctUntilChanged()
}