package com.anvios.android.repository.source.validation

import io.reactivex.Single

interface SignInRepository {

    fun signIn(signInData: SignInData): Single<String>

    data class SignInData(
        val username: String,
        val password: String
    )
}