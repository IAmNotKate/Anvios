package com.anvios.android.repository.source.save.add

interface AddDataRepository<T>{
    fun add(source: T)
}