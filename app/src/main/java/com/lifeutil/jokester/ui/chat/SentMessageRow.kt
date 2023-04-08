package com.lifeutil.jokester.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lifeutil.jokester.ui.theme.AiGreen
import com.lifeutil.jokester.ui.util.SubcomposeColumn

/**
 * This sent message row uses overloaded [SubcomposeColumn] function only with **content** arg
 */
@Composable
fun SentMessageRow(
    text: String,
    messageTime: String,
    modifier: Modifier = Modifier,
    messageStatus: MessageStatus = MessageStatus.READ
) {
    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 60.dp, end = 8.dp, top = 6.dp, bottom = 6.dp)
    ) {
        // This is chat bubble
        val roundedCornerShape = RoundedCornerShape(12.dp, 1.dp, 12.dp, 12.dp)
        SubcomposeColumn(
            modifier = Modifier
                .shadow(
                    8.dp,
                    roundedCornerShape,
                    ambientColor = AiGreen,
                    spotColor = Color.LightGray
                )
                .clip(roundedCornerShape)
                .background(Color.White)
                .clickable { },

            content = {
                ChatFlexBoxLayout(
                    modifier = Modifier.padding(
                        start = 2.dp,
                        top = 2.dp,
                        end = 4.dp,
                        bottom = 2.dp
                    ),
                    text = text,
                    messageStat = {
                        Text("") // hide for now
//                        MessageTimeText(
//                            modifier = Modifier.wrapContentSize(),
//                            messageTime = messageTime,
//                            messageStatus = messageStatus
//                        )
                    }
                )
            }
        )
    }
}