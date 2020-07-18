package com.anvios.android.repository.implementation.post

import com.anvios.android.model.Post
import com.anvios.android.model.User
import com.anvios.android.repository.implementation.mapper.DataMapper
import com.anvios.android.repository.source.obtain.page.ObtainPostsPageRepository
import com.anvios.android.web.anvios.data.response.PostResponse
import com.anvios.android.web.anvios.main.AnviosApi
import io.reactivex.Single

class ObtainPostsPageFromWebRepository(
    private val api: AnviosApi
) : ObtainPostsPageRepository {

    override fun obtain(page: Int): Single<List<Post>> {
        return api.getPostsPage(page)
            .map { postsResponse ->
                postsResponse.map {
                    PostResponseToModelMapper.map(it)
                }
            }
    }
}

private object PostResponseToModelMapper :
    DataMapper<PostResponse, Post> {
    override fun map(source: PostResponse): Post {
        return Post(
            source.postId,
            source.title,
            source.text,
            source.dateCreated,
            PostCreatorResponseToModelMapper.map(
                source.creator
            )
        )
    }
}

private object PostCreatorResponseToModelMapper :
    DataMapper<PostResponse.PostCreator, User> {
    override fun map(source: PostResponse.PostCreator): User {
        return User(source.userId, source.username)
    }
}