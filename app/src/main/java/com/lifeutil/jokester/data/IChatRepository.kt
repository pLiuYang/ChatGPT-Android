package com.lifeutil.jokester.data

import com.lifeutil.jokester.model.UiChatMessage
import com.lifeutil.jokester.model.UiConversation
import kotlinx.coroutines.flow.Flow

interface IChatRepository {

    suspend fun addUserMessage(messageText: String)

    suspend fun sendRequest(previousMessages: List<UiChatMessage>, newMessage: String)

    /**
     * Delete messages in this conversation
     */
    suspend fun deleteMessages(convoId: Long)

    fun getUiChatMessages(): Flow<List<UiChatMessage>>

    fun getConversation(): Flow<UiConversation>
}