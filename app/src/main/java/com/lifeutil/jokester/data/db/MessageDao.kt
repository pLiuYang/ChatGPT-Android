package com.lifeutil.jokester.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: DBMessage)

    @Query("DELETE FROM message_table")
    suspend fun deleteMessages()

    @Query("SELECT * FROM message_table WHERE conversation_id=:conversationId ORDER BY last_updated")
    fun getMessages(conversationId: Long): Flow<List<DBMessage>>
}