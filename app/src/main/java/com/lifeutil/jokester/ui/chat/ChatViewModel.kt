package com.lifeutil.jokester.ui.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.exception.OpenAITimeoutException
import com.lifeutil.jokester.data.IChatRepository
import com.lifeutil.jokester.data.OpenAIChatRepository
import com.lifeutil.jokester.model.AsstType
import com.lifeutil.jokester.model.UiAsstType
import com.lifeutil.jokester.model.UiConversation
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatViewModel(
    private val conversationId: Long,
    private val chatRepository: IChatRepository = OpenAIChatRepository(conversationId)
) : ViewModel() {

    val uiChatMessages = chatRepository.getUiChatMessages()
        .stateIn(viewModelScope, WhileSubscribed(), emptyList())
    val conversation = chatRepository.getConversation()
        .stateIn(viewModelScope, WhileSubscribed(), UiConversation(0, "", AsstType.DEFAULT, "", ""))

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "Handle $exception")
        if (exception is OpenAITimeoutException) {
            chatRepository.decrementRequestCount()
            viewModelScope.launch(Dispatchers.IO) {
                chatRepository.addSystemMessage("Sorry I don't understand, please elaborate")
            }
        }
    }

    fun addUserMessage(messageText: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            chatRepository.incrementRequestCount()
            chatRepository.addUserMessage(messageText)
            chatRepository.sendRequest(uiChatMessages.value, messageText, conversation.value.asstType)
        }
    }

    fun deleteAllMessages() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            chatRepository.deleteMessages(conversationId)
        }
    }

    fun updateConvoType(uiAsstType: UiAsstType) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            chatRepository.updateConversationType(uiAsstType)
        }
    }

    companion object {
        private const val TAG = "ChatViewModel"
    }
}

@Suppress("unchecked_cast")
class ChatViewModelFactory(
    private val conversationId: Long
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(conversationId) as T
    }
}