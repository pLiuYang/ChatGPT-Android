package com.lifeutil.jokester.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DB_NAME = "chat_database"

@Database(entities = [(DBMessage::class)], version = 1)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun messageDao(): ChatDao

    companion object {
        fun create(context: Context): ChatDatabase =
            Room.databaseBuilder(context, ChatDatabase::class.java, DB_NAME).build()
    }
}