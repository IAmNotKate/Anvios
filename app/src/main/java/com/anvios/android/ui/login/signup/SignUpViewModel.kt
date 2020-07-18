package com.anvios.android.ui.login.signup

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.anvios.android.R
import com.anvios.android.preferences.AppPreferences
import com.anvios.android.repository.source.validation.SignInRepository
import com.anvios.android.repository.implementation.validation.SignInRepositoryWebImpl
import com.anvios.android.repository.source.validation.SignUpRepository
import com.anvios.android.repository.implementation.validation.SignUpRepositoryWebImpl
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

private const val SIGN_UP_ERROR_TAG = "sign_up_error"

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = AppPreferences.getInstance(application.applicationContext)
    private val compositeDisposable = CompositeDisposable()
    private val signUpButtonEnabledSubject = BehaviorSubject.createDefault<Boolean>(false)
    private val signUpRepository: SignUpRepository = SignUpRepositoryWebImpl()
    private val signInRepository: SignInRepository = SignInRepositoryWebImpl()

    private val errorToast =
        Toast.makeText(application.applicationContext, R.string.sign_up_error, Toast.LENGTH_SHORT)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getSignUpButtonEnabledObservable(): Observable<Boolean> = signUpButtonEnabledSubject

    fun setSignUpButtonEnabled(isEnabled: Boolean) {
        signUpButtonEnabledSubject.onNext(isEnabled)
    }

    fun signUp(username: String, name: String, surname: String, email: String, password: String) {
        compositeDisposable.add(
            signUpRepository.signUp(
                SignUpRepository.SignUpData(
                    username,
                    name,
                    surname,
                    password,
                    email
                ), signInRepository
            )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ token ->
                    preferences.saveDeviceToken(token)
                }, { error ->
                    Log.e(SIGN_UP_ERROR_TAG, error.toString())
                    errorToast.show()
                })
        )
    }
}