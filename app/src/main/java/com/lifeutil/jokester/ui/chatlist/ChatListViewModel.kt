package com.lifeutil.jokester.ui.chatlist

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeutil.jokester.data.ChatListRepository
import com.lifeutil.jokester.ui.chatlist.ConversationConstants.NO_CONVERSATION_CREATED
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val chatListRepository: ChatListRepository = ChatListRepository()
) : ViewModel() {

    val uiConversations = chatListRepository.getUiConversations().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )
    val newConversationId = mutableStateOf(NO_CONVERSATION_CREATED)

    fun createConvo() {
        viewModelScope.launch {
            val res = chatListRepository.createConvo()
            Log.d(TAG, "insert returned id: $res")
            newConversationId.value = res
        }
    }

    fun deleteConvo(convoId: Long) {
        viewModelScope.launch {
            chatListRepository.deleteConvo(convoId)
        }
    }

    companion object {
        private const val TAG = "ChatListViewModel"
    }
}