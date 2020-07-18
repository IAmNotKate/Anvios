package com.anvios.android.database.anvios.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anvios.android.database.anvios.main.AnviosDataContract

@Entity(tableName = AnviosDataContract.User.TABLE_NAME)
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = AnviosDataContract.User.ID) val id: Int,
    @ColumnInfo(name = AnviosDataContract.User.USERNAME) val username: String
)