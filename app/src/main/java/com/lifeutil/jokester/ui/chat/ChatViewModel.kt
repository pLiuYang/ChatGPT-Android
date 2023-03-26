package com.lifeutil.jokester.ui.chat

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.lifeutil.jokester.OpenAIHelper.openAI
import com.lifeutil.jokester.model.UiChatMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    val uiChatMessages = mutableStateListOf<UiChatMessage>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "Handle $exception")
    }

    fun addUserMessage(messageText: String) {
        // add to list
        uiChatMessages.add(
            UiChatMessage(
                (uiChatMessages.size + 1).toLong(),
                messageText,
                System.currentTimeMillis()
            )
        )

        // send request
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            sendRequest()
        }
    }

    @OptIn(BetaOpenAI::class)
    private suspend fun sendRequest() {
        // add loading
        val loading = UiChatMessage(1000, "", 1, false, true)
        uiChatMessages.add(loading)

        val completionRequest = ChatCompletionRequest(
            model = ModelId("gpt-3.5-turbo"),
            // avoid mapping back to ChatMessage
            messages = uiChatMessages.map {
                if (it.fromMe) {
                    ChatMessage(role = ChatRole.User, content = it.message)
                } else {
                    ChatMessage(role = ChatRole.Assistant, content = it.message)
                }
            }
        )
        val completion = openAI.chatCompletion(completionRequest)
        Log.i(TAG, "token usage: prompt - ${completion.usage?.promptTokens}, completion - ${completion.usage?.completionTokens}")
        completion.choices.forEach {
            uiChatMessages.add(UiChatMessage(1, it.message?.content ?: "", 1, false))
        }

        // remove loading
        uiChatMessages.remove(loading)
    }

    companion object {
        private const val TAG = "ChatViewModel"
    }
}

/**
 * UI state for chat screen
 */
//sealed interface ChatUiState {
//    data class Success(val messages: List<Message>) : ChatUiState
//    object Loading : ChatUiState
//}