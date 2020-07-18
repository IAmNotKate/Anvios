package com.anvios.android.repository.source.obtain.page

import io.reactivex.Single

interface ObtainDataPageRepository<T> {
    fun obtain(page: Int): Single<List<T>>
}