package com.lifeutil.jokester.data

import android.util.Log
import com.lifeutil.jokester.DBHelper
import com.lifeutil.jokester.data.db.ConversationDao
import com.lifeutil.jokester.data.db.DBConversation
import com.lifeutil.jokester.model.AsstType
import com.lifeutil.jokester.model.UiConversation
import com.lifeutil.jokester.ui.util.toUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatListRepository {

    private val convoDao: ConversationDao by lazy { DBHelper.chatDatabase.conversationDao() }

    // TODO refresh timestamp every minute
    private val uiConversations: Flow<List<UiConversation>> =
        convoDao.getConversations().map { dbList ->
            Log.d(TAG, "DB convo updated, size ${dbList.size}")
            val now = System.currentTimeMillis()

            dbList.map {
                it.toUiModel(now)
            }
        }

    suspend fun createConvo(): Long {
        return convoDao.insertConversation(
            DBConversation(
                topic = "Omni-assistant ${DataConstants.getRandomEmoji()}",
                asstType = AsstType.DEFAULT.value,
                lastMessage = "Click to start chat",
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