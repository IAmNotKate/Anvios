package com.anvios.android.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anvios.android.preferences.AppPreferences
import com.anvios.android.ui.login.main.LoginActivity
import com.anvios.android.ui.mainmenu.main.MainMenuActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigate()
    }

    private fun navigate() {
        val preferences = AppPreferences.getInstance(applicationContext)

        preferences.saveCurrentCodeVersion()

        val intent = if (preferences.getDeviceToken().isNullOrBlank()) {
            Intent(this, LoginActivity::class.java)
        } else {
            Intent(this, MainMenuActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}