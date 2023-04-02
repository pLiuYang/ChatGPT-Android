package com.lifeutil.jokester.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertConversation(message: DBConversation): Long

    /**
     * Delete single conversation with id
     */
    @Query("DELETE FROM conversation_table WHERE id=:conversationId")
    suspend fun deleteConversation(conversationId: Long)

    @Query("SELECT * FROM conversation_table ORDER BY last_updated")
    fun getConversations(): Flow<List<DBConversation>>
}