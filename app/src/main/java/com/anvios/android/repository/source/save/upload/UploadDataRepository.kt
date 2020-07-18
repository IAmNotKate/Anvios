package com.anvios.android.repository.source.save.upload

import io.reactivex.Single

interface UploadDataRepository<T, S> {
    fun upload(source: T): Single<S>
}