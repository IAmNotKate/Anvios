package com.anvios.android.repository.source.save.add

import com.anvios.android.model.Post

interface AddPostsRepository :
    AddDataListRepository<Post> {
    override fun add(source: List<Post>)
}