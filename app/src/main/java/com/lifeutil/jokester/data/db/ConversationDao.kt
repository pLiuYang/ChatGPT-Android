package com.lifeutil.jokester.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lifeutil.jokester.model.AsstType
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

    @Query("SELECT * FROM conversation_table ORDER BY last_updated DESC")
    fun getConversations(): Flow<List<DBConversation>>

    @Query("SELECT * FROM conversation_table WHERE id=:conversationId")
    fun getConversation(conversationId: Long): Flow<DBConversation>

    @Query("Update conversation_table SET last_message=:messageText, last_updated=:lastUpdated WHERE id=:conversationId")
    fun updateConversationLastMessage(conversationId: Long, messageText: String, lastUpdated: Long)

    @Query("Update conversation_table SET last_message=:messageText WHERE id=:conversationId")
    fun updateConversationLastMessage(conversationId: Long, messageText: String)

    @Query("Update conversation_table SET asst_type=:asstType, topic=:newTopic WHERE id=:conversationId")
    fun updateConversationAsstType(conversationId: Long, asstType: Int, newTopic: String)
}