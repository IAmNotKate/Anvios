package com.anvios.android.repository.source.save.add

interface AddDataListRepository<T> :
    AddDataRepository<List<T>> {
    override fun add(source: List<T>)
}