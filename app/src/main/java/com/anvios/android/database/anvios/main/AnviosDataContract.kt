package com.anvios.android.database.anvios.main

interface AnviosDataContract {
    object Database {
        const val DB_NAME = "anvios_database"
    }

    object Post {
        const val TABLE_NAME = "post"
        const val ID = "id"
        const val USER_ID = "user_id"
        const val TITLE = "title"
        const val TEXT = "text"
        const val DATE_ADDED = "date_added"
    }

    object User {
        const val TABLE_NAME = "user"
        const val ID = "id"
        const val USERNAME = "username"
    }
}