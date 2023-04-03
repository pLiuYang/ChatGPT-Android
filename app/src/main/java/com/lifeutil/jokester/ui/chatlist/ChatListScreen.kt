package com.lifeutil.jokester.ui.chatlist

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lifeutil.jokester.ui.theme.AiGreen
import com.lifeutil.jokester.ui.theme.TealDeer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatListScreen(
    onNavigateToChat: (Long) -> Unit
) {
    val chatListViewModel: ChatListViewModel = viewModel()
    val conversations by chatListViewModel.uiConversations.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBarComponent(createConvo = {
                chatListViewModel.createConvo()
            })
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
    }
}

// testing
@Composable
private fun BottomAppBarComponent(createConvo: (() -> Unit)? = null) {
    BottomAppBar(
        containerColor = TealDeer,
        contentColor = Color.Gray,
        tonalElevation = 4.dp,
        actions = {
            val context = LocalContext.current
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .clickable {
                        Toast
                            .makeText(
                                context,
                                "Tips to your AI assistant. Free daily top up.",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Savings,
                    contentDescription = "",
                    modifier = Modifier.size(36.dp),
                    tint = Color(0xFFffcc33)
                )
                Text(text = "10 coins")
            }
            IconButton(onClick = { /* Settings onClick */ }) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "",
                    modifier = Modifier.size(36.dp),
                    tint = Color.LightGray
                )
            }
        },
        modifier = Modifier.clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { createConvo?.invoke() },
                containerColor = AiGreen
            ) {
                Icon(
                    Icons.Filled.Add, tint = Color.White,
                    contentDescription = null
                )
            }
        }
    )
}