package com.lifeutil.jokester.data

import com.lifeutil.jokester.model.UiChatMessage
import kotlinx.coroutines.flow.Flow

interface IChatRepository {

    suspend fun addUserMessage(messageText: String)

    suspend fun sendRequest(previousMessages: List<UiChatMessage>, newMessage: String)

    fun getUiChatMessages(): Flow<List<UiChatMessage>>
}