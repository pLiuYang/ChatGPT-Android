package com.lifeutil.jokester.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_table")
data class DBMessage(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "fromMe") val fromMe: Boolean,
)