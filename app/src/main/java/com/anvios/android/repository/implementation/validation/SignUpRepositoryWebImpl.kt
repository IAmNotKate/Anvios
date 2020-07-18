package com.anvios.android.repository.implementation.validation

import com.anvios.android.web.anvios.data.request.RegisterRequest
import com.anvios.android.web.anvios.main.AnviosNetworkService
import com.anvios.android.repository.implementation.mapper.DataMapper
import com.anvios.android.repository.source.validation.SignInRepository
import com.anvios.android.repository.source.validation.SignUpRepository
import io.reactivex.Single

class SignUpRepositoryWebImpl :
    SignUpRepository {
    override fun signUp(
        signUpData: SignUpRepository.SignUpData,
        signInRepository: SignInRepository
    ): Single<String> {
        return AnviosNetworkService().getApi()
            .register(
                SignUpDataMapper.map(
                    signUpData
                )
            )
            .flatMap {
                signInRepository.signIn(
                    SignInRepository.SignInData(
                        signUpData.username,
                        signUpData.password
                    )
                )
            }
    }
}

object SignUpDataMapper :
    DataMapper<SignUpRepository.SignUpData, RegisterRequest> {
    override fun map(source: SignUpRepository.SignUpData): RegisterRequest =
        RegisterRequest(
            source.username,
            source.password,
            source.name,
            source.surname,
            source.email
        )
}