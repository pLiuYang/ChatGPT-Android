package com.lifeutil.jokester.data

import com.lifeutil.jokester.model.UiChatMessage

interface IChatRepository {

    fun addUserMessage(messageText: String)

    fun getUiChatMessages(): List<UiChatMessage>
}