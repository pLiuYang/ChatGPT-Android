package com.lifeutil.jokester.data

import android.util.Log
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.lifeutil.jokester.DBHelper
import com.lifeutil.jokester.OpenAIHelper
import com.lifeutil.jokester.data.db.ChatDao
import com.lifeutil.jokester.data.db.DBMessage
import com.lifeutil.jokester.model.UiChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.atomic.AtomicInteger

class OpenAIChatRepository : IChatRepository {

    private val chatDao: ChatDao by lazy { DBHelper.chatDatabase.messageDao() }
    private val modelId by lazy { ModelId(GPT_3_5_TURBO) }
    private val requestCount: AtomicInteger = AtomicInteger(0)
    private val loadingFakeMsg = UiChatMessage(1000, "", 1, false, true)

    private val uiChatMessages: Flow<List<UiChatMessage>> = chatDao.getMessages().map { dbList ->
        dbListToUiModel(dbList)
    }

    override fun getUiChatMessages(): Flow<List<UiChatMessage>> = uiChatMessages

    override suspend fun addUserMessage(messageText: String) {
        // add into Database
        chatDao.insertMessage(
            DBMessage(
                message = messageText,
                date = System.currentTimeMillis(),
                fromMe = true
            )
        )
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun sendRequest(previousMessages: List<UiChatMessage>, newMessage: String) {
        // increase loading count
        requestCount.incrementAndGet()

        val completionRequest = ChatCompletionRequest(
            model = modelId,
            messages = getHistoryMessages(previousMessages, newMessage)
        )
        val completion = OpenAIHelper.openAI.chatCompletion(completionRequest)
        Log.i(
            TAG,
            "token usage: prompt - ${completion.usage?.promptTokens}, completion - ${completion.usage?.completionTokens}"
        )
        completion.choices.forEach {
            chatDao.insertMessage(
                DBMessage(
                    message = it.message?.content ?: "",
                    date = System.currentTimeMillis(),
                    fromMe = false
                )
            )
        }

        // decrease loading count
        requestCount.decrementAndGet()
    }

    @OptIn(BetaOpenAI::class)
    private fun getHistoryMessages(
        previousMessages: List<UiChatMessage>,
        newMessage: String
    ): List<ChatMessage> = previousMessages.map {
        if (it.fromMe) {
            ChatMessage(role = ChatRole.User, content = it.message)
        } else {
            ChatMessage(role = ChatRole.Assistant, content = it.message)
        }
    } + ChatMessage(role = ChatRole.User, content = newMessage)

    private fun dbListToUiModel(dbMessages: List<DBMessage>): List<UiChatMessage> {
        val res = mutableListOf<UiChatMessage>()
        dbMessages.forEach { dbMessage ->
            res.add(
                UiChatMessage(
                    dbMessage.id,
                    dbMessage.message,
                    dbMessage.date,
                    dbMessage.fromMe
                )
            )
        }
        if (isLoading()) res.add(loadingFakeMsg)
        return res
    }

    private fun isLoading() = requestCount.get() != 0

    companion object {
        private const val TAG = "OpenAIChatRepository"
        private const val GPT_3_5_TURBO = "gpt-3.5-turbo"
    }
}