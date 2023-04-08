package com.lifeutil.jokester.ui.chat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lifeutil.jokester.model.AsstType
import com.lifeutil.jokester.model.UiChatMessage
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    conversationId: Long,
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = viewModel(factory = ChatViewModelFactory(conversationId)),
    onBack: (() -> Unit)? = null
) {
    val messages by chatViewModel.uiChatMessages.collectAsStateWithLifecycle()
    val conversation by chatViewModel.conversation.collectAsStateWithLifecycle()
    val sdf = remember { SimpleDateFormat("hh:mm a", Locale.ROOT) }
//    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFf8f8f8), Color(0xFFfdfdfd))
                )
            )
    ) {
        val scrollState = rememberLazyListState()

        ChatAppBar(
            title = conversation.topic,
            onBack = onBack,
            onEdit = { /* TODO */ },
            onClearChat = { chatViewModel.deleteAllMessages() }
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
            if (conversation.asstType == AsstType.DEFAULT) {
                item {
                   TopicHint()
                }
            }

            items(messages, key = { it.id }) { message: UiChatMessage ->
                if (message.fromMe) {
                    SentMessageRow(
                        text = message.message,
                        messageTime = sdf.format(message.lastUpdated),
                        modifier = Modifier.animateItemPlacement()
                    )
                } else {
                    ReceivedMessageRow(
                        text = message.message,
                        messageTime = sdf.format(message.lastUpdated),
                        isLoading = message.isLoading,
                        modifier = Modifier.animateItemPlacement()
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