package com.anvios.android.database.anvios.entity

import androidx.room.*
import com.anvios.android.database.anvios.main.AnviosDataContract
import com.anvios.android.database.anvios.converter.CalendarConverter
import java.util.*

@Entity(tableName = AnviosDataContract.Post.TABLE_NAME)
@ForeignKey(
    entity = UserEntity::class,
    parentColumns = [AnviosDataContract.User.ID],
    childColumns = [AnviosDataContract.Post.USER_ID]
)
@TypeConverters(CalendarConverter::class)
data class PostEntity(
    @PrimaryKey @ColumnInfo(name = AnviosDataContract.Post.ID) val id: Int,
    @ColumnInfo(name = AnviosDataContract.Post.USER_ID) val userId: Int,
    @ColumnInfo(name = AnviosDataContract.Post.TITLE) val title: String,
    @ColumnInfo(name = AnviosDataContract.Post.TEXT) val text: String,
    @ColumnInfo(name = AnviosDataContract.Post.DATE_ADDED) val dateAdded: Calendar
)