package com.anvios.android.repository.implementation.mapper

interface DataMapper<T, S> {
    fun map(source: T): S
}