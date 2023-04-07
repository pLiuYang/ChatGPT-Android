package com.lifeutil.jokester.ui.chatlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifeutil.jokester.model.UiConversation

@Composable
fun ConversationRow(conversation: UiConversation, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(8.dp),
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
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${conversation.topic} ${conversation.id}",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "${conversation.lastUpdated}",
                    color = Color.LightGray
                )
            }

            Text(
                text = conversation.lastMessage,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 4.dp, bottom = 6.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewConversationRow() {
    ConversationRow(
        modifier = Modifier.fillMaxWidth(),
        conversation = UiConversation(1, "translator", "last message", "today")
    )
}