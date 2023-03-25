package com.lifeutil.jokester.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.lifeutil.jokester.ui.theme.SentMessageColor
import com.lifeutil.jokester.ui.util.SubcomposeColumn

/**
 * This sent message row uses overloaded [SubcomposeColumn] function only with **content** arg
 */
@Composable
fun SentMessageRow(
    text: String,
    messageTime: String,
    messageStatus: MessageStatus = MessageStatus.READ
) {

    println("🚕 SentMessageRowAlt()")

    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 60.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)


    ) {


        // This is chat bubble
        SubcomposeColumn(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(SentMessageColor)
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