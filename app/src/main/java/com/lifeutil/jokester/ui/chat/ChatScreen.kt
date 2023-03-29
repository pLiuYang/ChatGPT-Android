package com.lifeutil.jokester.ui.chat

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lifeutil.jokester.model.ChatUiState
import com.lifeutil.jokester.model.UiChatMessage
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ChatScreen(
    onBack: (() -> Unit)? = null
) {
    val chatViewModel: ChatViewModel = viewModel()

    val messages by chatViewModel.uiChatMessages.collectAsStateWithLifecycle()
    val sdf = remember { SimpleDateFormat("hh:mm a", Locale.ROOT) }
//    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFf8f8f8), Color(0xFFfdfdfd))
                )
            )
    ) {
        val scrollState = rememberLazyListState()

        ChatAppBar(
            title = "小助手",
            onBack = onBack,
            onEdit = { chatViewModel.deleteAllMessages() }
        )

//        when (chatUiState) {
//            is ChatUiState.Success -> {
//                val messages = (chatUiState as ChatUiState.Success).messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = scrollState,
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
        ) {
            items(messages) { message: UiChatMessage ->
                if (message.fromMe) {
                    SentMessageRow(
                        text = message.message,
                        messageTime = sdf.format(message.date)
                    )
                } else {
                    ReceivedMessageRow(
                        text = message.message,
                        messageTime = sdf.format(message.date),
                        isLoading = message.isLoading
                    )
                }
            }
        }
        LaunchedEffect(messages.size) {
            if (messages.isEmpty()) return@LaunchedEffect

            // Scroll to bottom
            scrollState.animateScrollToItem(messages.size - 1)
        }
//            }

//            is ChatUiState.Loading -> {}
//        }

        ChatInput(
            modifier = Modifier.imePadding(),
            onMessageChange = { messageContent ->
                // Update message list
                chatViewModel.addUserMessage(messageContent)

//                // Scroll to bottom
//                coroutineScope.launch {
//                    scrollState.animateScrollToItem(messages.size - 1)
//                }
            })
    }
}