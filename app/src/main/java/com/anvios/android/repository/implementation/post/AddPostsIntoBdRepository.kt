package com.anvios.android.repository.implementation.post

import com.anvios.android.database.anvios.entity.PostEntity
import com.anvios.android.database.anvios.entity.UserEntity
import com.anvios.android.database.anvios.main.AnviosDao
import com.anvios.android.database.anvios.relation.PostWithUserRelation
import com.anvios.android.model.Post
import com.anvios.android.model.User
import com.anvios.android.repository.implementation.mapper.DataMapper
import com.anvios.android.repository.source.save.add.AddPostsRepository
import kotlin.collections.ArrayList

class AddPostsIntoBdRepository(private val anviosDao: AnviosDao) :
    AddPostsRepository {

    override fun add(source: List<Post>) {
        val posts = source.map {
            PostWithUserRelation(
                PostModelToEntityMapper.map(it),
                UserModelToEntityMapper.map(it.user)
            )
        }
        anviosDao.insert(posts)
    }
}

private object PostModelToEntityMapper :
    DataMapper<Post, PostEntity> {
    override fun map(source: Post): PostEntity {
        return PostEntity(
            source.postId,
            source.user.userId,
            source.title,
            source.text,
            source.dateCreated
        )
    }
}

private object UserModelToEntityMapper :
    DataMapper<User, UserEntity> {
    override fun map(source: User): UserEntity {
        return UserEntity(source.userId, source.username)
    }
}