package com.lifeutil.jokester.ui.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TopicHint(modifier: Modifier = Modifier, onClickAction: (() -> Unit)? = null) {
    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    Box(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
            .drawBehind {
                drawRoundRect(
                    color = Color.LightGray,
                    style = stroke,
                    cornerRadius = CornerRadius(12.dp.toPx())
                )
            }
            .clickable {
                onClickAction?.invoke()
            },
        contentAlignment = Alignment.Center,

        ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Set a topic to get tailored replies.",
            color = Color.Gray,
            modifier = Modifier.padding(12.dp)
        )
    }
}