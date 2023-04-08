package com.lifeutil.jokester.data

import android.util.Log
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.lifeutil.jokester.DBHelper
import com.lifeutil.jokester.OpenAIHelper
import com.lifeutil.jokester.data.db.DBMessage
import com.lifeutil.jokester.data.db.MessageDao
import com.lifeutil.jokester.model.UiChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.atomic.AtomicInteger

class OpenAIChatRepository(private val conversationId: Long) : IChatRepository {

    private val messageDao: MessageDao by lazy { DBHelper.chatDatabase.messageDao() }
    private val conversationDao by lazy { DBHelper.chatDatabase.conversationDao() }
    private val modelId by lazy { ModelId(GPT_3_5_TURBO) }
    private val requestCount: AtomicInteger = AtomicInteger(0)
    private val loadingFakeMsg = UiChatMessage(1000, "", 1, false, true)

    private val uiChatMessages: Flow<List<UiChatMessage>> =
        messageDao.getMessages(conversationId).map { dbList ->
            dbListToUiModel(dbList)
        }

    private val chatTopic: Flow<String> =
        conversationDao.getConversation(conversationId).map { it.topic }

    override fun getUiChatMessages(): Flow<List<UiChatMessage>> = uiChatMessages
    override fun getChatTopic(): Flow<String> = chatTopic

    override suspend fun addUserMessage(messageText: String) {
        // add into Database
        messageDao.insertMessage(
            DBMessage(
                conversationId = conversationId,
                message = messageText,
                lastUpdated = System.currentTimeMillis(),
                fromMe = true
            )
        )

        // side effect
        updateConvoLastMessage(messageText)
    }

    @OptIn(BetaOpenAI::class)
    override suspend fun sendRequest(previousMessages: List<UiChatMessage>, newMessage: String) {
        // increase loading count
        requestCount.incrementAndGet()

        val historyMessages = getHistoryMessages(previousMessages, newMessage)
//        val systemMessage = ChatMessage(role = ChatRole.System, content = "You are an English to Chinese dictionary. Display meaning of the words with sample")
        val completionRequest = ChatCompletionRequest(
            model = modelId,
            messages = historyMessages
//            messages = listOf(systemMessage) + historyMessages
        )
        val completion = OpenAIHelper.openAI.chatCompletion(completionRequest)
        handleCompletionResponse(completion)

        // side effect
        completion.choices.lastOrNull()?.let {
            it.message?.content?.let { text ->
                updateConvoLastMessage(text)
            }
        }

        // decrease loading count
        requestCount.decrementAndGet()
    }

    override suspend fun deleteMessages(convoId: Long) {
        messageDao.deleteMessages(convoId)
    }

    @OptIn(BetaOpenAI::class)
    private suspend fun handleCompletionResponse(completion: ChatCompletion) {
        Log.i(
            TAG,
            "token usage: prompt - ${completion.usage?.promptTokens}, completion - ${completion.usage?.completionTokens}"
        )
        completion.choices.forEach {
            messageDao.insertMessage(
                DBMessage(
                    conversationId = conversationId,
                    message = it.message?.content ?: "",
                    lastUpdated = System.currentTimeMillis(),
                    fromMe = false
                )
            )
        }
    }

    private fun updateConvoLastMessage(messageText: String) {
        conversationDao.updateConversationLastMessage(
            conversationId,
            messageText,
            System.currentTimeMillis()
        )
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
                    dbMessage.lastUpdated,
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
//        private const val DAVINCI_003 = "text-davinci-003"
    }
}