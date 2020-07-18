package com.anvios.android.database.anvios.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.anvios.android.database.anvios.entity.PostEntity
import com.anvios.android.database.anvios.entity.UserEntity
import com.anvios.android.database.anvios.main.AnviosDataContract

data class PostWithUserRelation(
    @Embedded val post: PostEntity,
    @Relation(
        parentColumn = AnviosDataContract.Post.USER_ID,
        entityColumn = AnviosDataContract.User.ID,
        entity = UserEntity::class
    )
    val user: UserEntity
)