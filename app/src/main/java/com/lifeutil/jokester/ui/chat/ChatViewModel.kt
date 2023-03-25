package com.lifeutil.jokester.ui.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.lifeutil.jokester.model.ChatMessage

class ChatViewModel : ViewModel() {
    val chatMessages = mutableStateListOf<ChatMessage>()

    init {
//        chatMessages.add(ChatMessage("hello"))
//        chatMessages.add(ChatMessage("hello back", false))
//        chatMessages.add(ChatMessage("hello back2"))
    }
}

/**
 * UI state for chat screen
 */
//sealed interface ChatUiState {
//    data class Success(val messages: List<Message>) : ChatUiState
//    object Loading : ChatUiState
//}