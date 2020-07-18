package com.anvios.android.repository.source.obtain.instance

import com.anvios.android.model.Post
import io.reactivex.Observable

interface ObtainPostsRepository :
    ObtainDataListRepository<Post> {
    override fun obtain(): Observable<MutableList<Post>>
}