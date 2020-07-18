package com.anvios.android.repository.implementation.validation

import com.anvios.android.web.anvios.data.request.LoginRequest
import com.anvios.android.web.anvios.data.response.LoginResponse
import com.anvios.android.web.anvios.main.AnviosNetworkService
import com.anvios.android.repository.implementation.mapper.DataMapper
import com.anvios.android.repository.source.validation.SignInRepository
import io.reactivex.Single
import java.io.IOException

class SignInRepositoryWebImpl : SignInRepository {

    override fun signIn(signInData: SignInRepository.SignInData): Single<String> {
        return AnviosNetworkService().getApi()
            .login(
                SignInDataMapper.map(
                    signInData
                )
            )
            .map { t: LoginResponse ->
                if (t.deviceToken.isNotEmpty()) {
                    t.deviceToken
                } else {
                    throw IOException()
                }
            }
    }
}

object SignInDataMapper :
    DataMapper<SignInRepository.SignInData, LoginRequest> {
    override fun map(source: SignInRepository.SignInData): LoginRequest =
        LoginRequest(source.username, source.username)
}