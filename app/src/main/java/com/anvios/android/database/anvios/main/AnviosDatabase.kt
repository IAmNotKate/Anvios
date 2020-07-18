package com.anvios.android.database.anvios.main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anvios.android.database.anvios.entity.PostEntity
import com.anvios.android.database.anvios.entity.UserEntity

@Database(entities = [UserEntity::class, PostEntity::class], version = 1)
abstract class AnviosDatabase : RoomDatabase() {

    abstract fun anviosDao(): AnviosDao

    companion object {
        @JvmStatic
        private var INSTANCE: AnviosDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): AnviosDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AnviosDatabase::class.java,
                    AnviosDataContract.Database.DB_NAME
                ).build()
            }
            return INSTANCE!!
        }
    }
}