package com.lifeutil.jokester.ui.util

import android.text.format.DateUtils
import com.lifeutil.jokester.data.db.DBConversation
import com.lifeutil.jokester.data.db.DBMessage
import com.lifeutil.jokester.model.AsstType
import com.lifeutil.jokester.model.UiChatMessage
import com.lifeutil.jokester.model.UiConversation

fun DBMessage.toUiModel(): UiChatMessage =
    UiChatMessage(
        id,
        message,
        lastUpdated,
        fromMe
    )

fun DBConversation.toUiModel(now: Long): UiConversation {
    return UiConversation(
        id,
        topic,
        AsstType.fromInt(asstType),
        lastMessage,
        getRelativeDateTime(now, lastUpdated)
    )
}

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