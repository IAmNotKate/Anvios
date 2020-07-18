package com.anvios.android.database.anvios.main

import androidx.room.*
import com.anvios.android.database.anvios.entity.PostEntity
import com.anvios.android.database.anvios.entity.UserEntity
import com.anvios.android.database.anvios.relation.PostWithUserRelation
import io.reactivex.Observable

@Dao
abstract class AnviosDao {
    @Query("SELECT * FROM POST")
    abstract fun selectPostsWithUser(): Observable<List<PostWithUserRelation>>

    @Transaction
    open fun insert(posts: List<PostWithUserRelation>) {
        posts.forEach {
            insert(it.post)
            insert(it.user)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(postEntity: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(userEntity: UserEntity)
}