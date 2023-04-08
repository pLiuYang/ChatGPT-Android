package com.lifeutil.jokester.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversation_table")
data class DBConversation(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "topic") val topic: String, // title/name of the conversation
    @ColumnInfo(name = "asst_type") val asstType: Int,
    @ColumnInfo(name = "last_message") val lastMessage: String,
    @ColumnInfo(name = "last_updated") val lastUpdated: Long,
)