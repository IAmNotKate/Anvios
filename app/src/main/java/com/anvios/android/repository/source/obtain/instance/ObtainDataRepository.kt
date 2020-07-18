package com.anvios.android.repository.source.obtain.instance

import io.reactivex.Observable

interface ObtainDataRepository<T> {
    fun obtain(): Observable<T>
}