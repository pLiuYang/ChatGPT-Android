package com.lifeutil.jokester.data

import com.lifeutil.jokester.model.AsstType
import com.lifeutil.jokester.model.UiAsstType
import com.lifeutil.jokester.model.UiChatMessage
import com.lifeutil.jokester.model.UiConversation
import kotlinx.coroutines.flow.Flow

interface IChatRepository {

    suspend fun addUserMessage(messageText: String)

    suspend fun addSystemMessage(messageText: String)

    suspend fun sendRequest(
        previousMessages: List<UiChatMessage>,
        newMessage: String,
        asstType: AsstType
    )

    fun incrementRequestCount()

    fun decrementRequestCount()

    /**
     * Delete messages in this conversation
     */
    suspend fun deleteMessages(convoId: Long)

    fun getUiChatMessages(): Flow<List<UiChatMessage>>

    fun getConversation(): Flow<UiConversation>

    suspend fun updateConversationType(asstType: UiAsstType)
}