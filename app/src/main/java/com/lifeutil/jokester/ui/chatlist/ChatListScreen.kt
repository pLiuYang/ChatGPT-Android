package com.lifeutil.jokester.ui.chatlist

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lifeutil.jokester.ui.chatlist.ConversationConstants.NO_CONVERSATION_CREATED

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatListScreen(
    onNavigateToChat: (Long) -> Unit,
    modifier: Modifier = Modifier,
    chatListViewModel: ChatListViewModel = viewModel()
) {
    val conversations by chatListViewModel.uiConversations.collectAsStateWithLifecycle()
    val newConversationId by remember { chatListViewModel.newConversationId }
    val scrollState = rememberLazyListState()

    // BottomSheet
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val modalSheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            ChatListBottomAppBar(
                createConvo = { chatListViewModel.createConvo() },
                openSettings = { openBottomSheet = true }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFf8f8f8), Color(0xFFfdfdfd))
                    )
                ),
            state = scrollState,
            contentPadding = PaddingValues(12.dp)
        ) {
            Log.d("ChatListScreen", "recompose LazyColumn, size: ${conversations.size}")
            items(conversations, key = { it.id }) { conversation ->
                val currentItem by rememberUpdatedState(newValue = conversation)
                val dismissState = rememberDismissState(
                    confirmValueChange = {
                        if (it == DismissValue.DismissedToStart) {
                            println("confirmValueChange $it")
                            chatListViewModel.deleteConvo(currentItem.id)
                            true
                        } else {
                            false
                        }
                    },
                    positionalThreshold = { totalDistance -> totalDistance * 0.3f } // 30% of the total width
                )
                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier.animateItemPlacement(),
                    background = { ConversationSwipeBackground() },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissContent = {
                        ConversationRow(
                            modifier = Modifier
                                .padding(5.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFf0f0f0))
                                .clickable { onNavigateToChat.invoke(conversation.id) },
                            conversation = conversation
                        )
                    })

            }
        }

        LaunchedEffect(key1 = newConversationId) {
            Log.d("ChatListScreen", "newConversationId $newConversationId")
            if (newConversationId != NO_CONVERSATION_CREATED) {
                onNavigateToChat.invoke(newConversationId)
                // reset
                chatListViewModel.newConversationId.value = NO_CONVERSATION_CREATED
            }
        }

        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                sheetState = modalSheetState,
                content = {
                    val settings = listOf("Top up coins", "Pro mode", "Share to friends")
                    settings.forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Tune,
                                contentDescription = "",
                                modifier = Modifier.padding(12.dp),
                                tint = Color.Gray
                            )
                            Text(
                                text = it,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }
                }
            )
        }
    }
}