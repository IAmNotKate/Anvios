package com.anvios.android.repository.source.validation

import io.reactivex.Single

interface SignUpRepository {

    fun signUp(
        signUpData: SignUpData,
        signInRepository: SignInRepository
    ): Single<String>

    data class SignUpData(
        val username: String,
        val name: String,
        val surname: String,
        val email: String,
        val password: String
    )
}