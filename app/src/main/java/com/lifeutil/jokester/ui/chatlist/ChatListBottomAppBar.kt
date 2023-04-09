package com.lifeutil.jokester.ui.chatlist

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lifeutil.jokester.ui.theme.AiGreen
import com.lifeutil.jokester.ui.theme.TealDeer

@Composable
fun ChatListBottomAppBar(
    modifier: Modifier = Modifier,
    createConvo: (() -> Unit)? = null,
    openSettings: (() -> Unit)? = null
) {
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
                Text(text = "1000 coins")
            }
            IconButton(onClick = { openSettings?.invoke() }) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "",
                    modifier = Modifier.size(36.dp),
                    tint = Color.LightGray
                )
            }
        },
        modifier = modifier.clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
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