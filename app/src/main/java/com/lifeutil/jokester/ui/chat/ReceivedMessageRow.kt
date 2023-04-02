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
import com.lifeutil.jokester.ui.util.ThreeDotsLoading

@Composable
fun ReceivedMessageRow(
    text: String,
    messageTime: String,
    isLoading: Boolean = false,
    modifier: Modifier
) {
    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 8.dp, end = 60.dp, top = 6.dp, bottom = 6.dp)

    ) {
        // This is chat bubble
        val roundedCornerShape = RoundedCornerShape(1.dp, 12.dp, 12.dp, 12.dp)
        SubcomposeColumn(
            modifier = modifier
                .shadow(
                    8.dp, roundedCornerShape,
                    ambientColor = Color.LightGray,
                    spotColor = Color.LightGray
                )
                .clip(roundedCornerShape)
                .background(AiGreen)
                .clickable { },
            content = {
                if (isLoading) {
                    ThreeDotsLoading(Color.White)
                } else {
                    ChatFlexBoxLayout(
                        modifier = Modifier.padding(start = 2.dp, end = 4.dp),
                        text = text,
                        color = Color.White,
                        messageStat = {
                            Text("") // hide for now
//                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//                            Text(
//                                modifier = Modifier.padding(top = 1.dp, bottom = 1.dp, end = 4.dp),
//                                text = messageTime,
//                                fontSize = 12.sp
//                            )
//                        }
                        }
                    )
                }
            }
        )
    }
}