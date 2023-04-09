package com.lifeutil.jokester.ui.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifeutil.jokester.model.UiAsstType

@Composable
fun ConversationTypeRow(
    uiAsstType: UiAsstType,
    modifier: Modifier = Modifier,
    clickAction: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 4.dp)
            .clickable {
                clickAction?.invoke()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            uiAsstType.icon,
            contentDescription = "",
            modifier = Modifier.padding(12.dp),
            tint = Color.Gray
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = uiAsstType.title,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(text = uiAsstType.hint, fontSize = 14.sp, color = Color.Gray)
        }
    }
}