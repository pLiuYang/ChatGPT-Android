package com.lifeutil.jokester.data

import android.text.format.DateUtils
import android.util.Log
import com.lifeutil.jokester.DBHelper
import com.lifeutil.jokester.data.db.ConversationDao
import com.lifeutil.jokester.data.db.DBConversation
import com.lifeutil.jokester.model.UiConversation
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
                UiConversation(it.id, it.topic, it.lastMessage, getRelativeDateTime(now, it.lastUpdated))
            }
        }

    suspend fun createConvo(): Long {
        return convoDao.insertConversation(
            DBConversation(
                topic = "topic",
                context = "context",
                lastMessage = "",
                lastUpdated = System.currentTimeMillis()
            )
        )
    }

    suspend fun deleteConvo(convoId: Long) {
        return convoDao.deleteConversation(convoId)
    }

    fun getUiConversations(): Flow<List<UiConversation>> = uiConversations

    private fun getRelativeDateTime(now: Long, lastUpdated: Long): CharSequence {
        val timeDiff = now - lastUpdated
        return if (timeDiff <= DateUtils.MINUTE_IN_MILLIS) {
            "just now"
        } else {
            DateUtils.getRelativeTimeSpanString(
                lastUpdated,
                now,
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE
            )
        }
    }

    companion object {
        private const val TAG = "ChatListRepository"
    }
}