package com.lifeutil.jokester.ui.chatlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeutil.jokester.data.ChatListRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val chatListRepository: ChatListRepository = ChatListRepository()
) : ViewModel() {

    val uiConversations = chatListRepository.getUiConversations().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )

    fun createConvo() {
        viewModelScope.launch {
            val res = chatListRepository.createConvo()
            Log.d(TAG, "insert returned id: $res")
            // navigate to new convo
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