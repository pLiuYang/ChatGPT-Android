package com.lifeutil.jokester.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lifeutil.jokester.model.ChatMessage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ChatScreen() {

    val messages = remember { mutableStateListOf<ChatMessage>() }
    val sdf = remember { SimpleDateFormat("hh:mm a", Locale.ROOT) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffFBE9E7))
    ) {

        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        ChatAppBar(
            title = "Sample chat room"
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = scrollState,
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
        ) {
            items(messages) { message: ChatMessage ->
                when (message.id.toInt() % 2) {
                    0 -> {
                        SentMessageRow(
                            text = message.message,
                            messageTime = sdf.format(message.date)
                        )
                    }
                    else -> {
                        ReceivedMessageRow(
                            text = message.message,
                            messageTime = sdf.format(message.date)
                        )
                    }
                }
            }
        }

        ChatInput(
            modifier = Modifier.imePadding(),
            onMessageChange = { messageContent ->
                messages.add(
                    ChatMessage(
                        (messages.size + 1).toLong(),
                        messageContent,
                        System.currentTimeMillis()
                    )
                )

                coroutineScope.launch {
                    scrollState.animateScrollToItem(messages.size - 1)
                }
            })
    }
}