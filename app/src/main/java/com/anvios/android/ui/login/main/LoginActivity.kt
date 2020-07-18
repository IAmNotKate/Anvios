package com.anvios.android.ui.login.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.anvios.android.R
import com.anvios.android.ui.mainmenu.main.MainMenuActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val controller = Navigation.findNavController(this, navHostLoginFragment.id)
        NavigationUI.setupWithNavController(loginToolbar, controller)

        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        compositeDisposable.add(viewModel.getNavigationObservable()
            .subscribe {
                if (it) {
                    startActivity(Intent(this, MainMenuActivity::class.java))
                    finish()
                }
            }
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}