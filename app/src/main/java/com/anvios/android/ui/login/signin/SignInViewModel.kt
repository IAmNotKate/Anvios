package com.anvios.android.ui.login.signin

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.anvios.android.R
import com.anvios.android.preferences.AppPreferences
import com.anvios.android.repository.source.validation.SignInRepository
import com.anvios.android.repository.implementation.validation.SignInRepositoryWebImpl
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

private const val SIGN_IN_ERROR_TAG = "sign_in_error"

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = AppPreferences.getInstance(application.applicationContext)
    private val compositeDisposable = CompositeDisposable()
    private val signInButtonEnabledSubject = BehaviorSubject.createDefault<Boolean>(false)
    private val signInRepository: SignInRepository = SignInRepositoryWebImpl()

    private val errorToast =
        Toast.makeText(application.applicationContext, R.string.sign_in_error, Toast.LENGTH_SHORT)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getSignInButtonEnabledObservable(): Observable<Boolean> = signInButtonEnabledSubject

    fun setSignInButtonEnabled(isEnabled: Boolean) {
        signInButtonEnabledSubject.onNext(isEnabled)
    }

    fun signIn(username: String, password: String) {
        compositeDisposable.add(
            signInRepository.signIn(SignInRepository.SignInData(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ token ->
                    preferences.saveDeviceToken(token)
                }, { error ->
                    Log.e(SIGN_IN_ERROR_TAG, error.toString())
                    errorToast.show()
                })
        )
    }
}