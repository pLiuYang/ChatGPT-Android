package com.lifeutil.jokester.data

import android.util.Log
import com.lifeutil.jokester.DBHelper
import com.lifeutil.jokester.data.db.ConversationDao
import com.lifeutil.jokester.data.db.DBConversation
import com.lifeutil.jokester.model.UiConversation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatListRepository {

    private val convoDao: ConversationDao by lazy { DBHelper.chatDatabase.conversationDao() }

    private val uiConversations: Flow<List<UiConversation>> =
        convoDao.getConversations().map { dbList ->
            Log.d(TAG, "DB convo updated, size ${dbList.size}")
            dbList.map {
                // todo last message
                UiConversation(it.id, it.topic, "", it.lastUpdated)
            }
        }

    suspend fun createConvo(): Long {
        return convoDao.insertConversation(
            DBConversation(
                topic = "topic",
                context = "context",
                lastUpdated = System.currentTimeMillis()
            )
        )
    }

    suspend fun deleteConvo(convoId: Long) {
        return convoDao.deleteConversation(convoId)
    }

    fun getUiConversations(): Flow<List<UiConversation>> = uiConversations

    companion object {
        private const val TAG = "ChatListRepository"
    }
}