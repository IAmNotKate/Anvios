package com.anvios.android.repository.source.obtain.instance

import io.reactivex.Observable

interface ObtainDataListRepository<T> :
    ObtainDataRepository<MutableList<T>> {
    override fun obtain(): Observable<MutableList<T>>
}