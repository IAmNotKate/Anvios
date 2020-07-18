package com.anvios.android.repository.implementation.post

import com.anvios.android.database.anvios.main.AnviosDao
import com.anvios.android.database.anvios.relation.PostWithUserRelation
import com.anvios.android.model.Post
import com.anvios.android.model.User
import com.anvios.android.repository.implementation.mapper.DataMapper
import com.anvios.android.repository.source.obtain.instance.ObtainPostsRepository
import io.reactivex.Observable

class ObtainRecyclerPostsFromDbRepository(
    private val anviosDao: AnviosDao
) : ObtainPostsRepository {

    override fun obtain(): Observable<MutableList<Post>> {
        return anviosDao.selectPostsWithUser()
            .map { postsView ->
                val posts: MutableList<Post> = ArrayList()
                postsView.forEach {
                    posts.add(
                        PostRelationToModelMapper.map(
                            it
                        )
                    )
                }
                posts
            }
    }
}

private object PostRelationToModelMapper :
    DataMapper<PostWithUserRelation, Post> {
    override fun map(source: PostWithUserRelation): Post {
        return Post(
            source.post.id,
            source.post.title,
            source.post.text,
            source.post.dateAdded,
            User(source.user.id, source.user.username)
        )
    }
}