package com.lifeutil.jokester.ui.chatlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lifeutil.jokester.model.UiConversation

@Composable
fun ConversationRow(modifier: Modifier, conversation: UiConversation) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = Icons.Filled.SupportAgent,
            contentDescription = "icon",
            modifier = Modifier
                .padding(12.dp)
                .size(36.dp),
            alpha = 0.6f
        )

        Column(
            Modifier
                .weight(1f)
                .padding(end = 12.dp)
        ) {
            Text(
                text = "Chat bot ${conversation.id}",
            )
            Text(
                text = conversation.topic,
                color = Color.LightGray
            )
        }
    }
}