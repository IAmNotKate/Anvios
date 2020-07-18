package com.anvios.android.repository.source.obtain.page

import com.anvios.android.model.Post
import io.reactivex.Single

interface ObtainPostsPageRepository :
    ObtainDataPageRepository<Post> {
    override fun obtain(page: Int): Single<List<Post>>
}