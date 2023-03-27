package com.lifeutil.jokester.ui.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeutil.jokester.DBHelper
import com.lifeutil.jokester.data.IChatRepository
import com.lifeutil.jokester.data.OpenAIChatRepository
import com.lifeutil.jokester.model.UiChatMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatRepository: IChatRepository = OpenAIChatRepository()
) : ViewModel() {

//    private var loadingMessage: Flow<UiChatMessage> = flow {
//        UiChatMessage(1000, "", 1, false, true)
//    }
    val uiChatMessages = chatRepository.getUiChatMessages()
        .stateIn(viewModelScope, WhileSubscribed(), emptyList())
//    var uiChatMessages = repoMessages.combine(loadingMessage) { list, loading ->
//        list + loading
//    }.stateIn(viewModelScope, WhileSubscribed(), emptyList())

//    val chatUiState = MutableStateFlow<ChatUiState>(ChatUiState.Loading)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "Handle $exception")
    }

    fun addUserMessage(messageText: String) {
//        if (uiChatMessages.value !is ChatUiState.Success) return

//        val loading = UiChatMessage(1000, "", 1, false, true)
//        uiChatMessages.value.update(loading)
//        loadingMessage = flow { emit(loading) }

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            chatRepository.addUserMessage(messageText)
            chatRepository.sendRequest(uiChatMessages.value, messageText)
        }
    }

    fun deleteAllMessages() {
        viewModelScope.launch {
            DBHelper.chatDatabase.messageDao().deleteMessages()
        }
    }

    companion object {
        private const val TAG = "ChatViewModel"
    }
}