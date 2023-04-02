package com.lifeutil.jokester.ui.chat

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lifeutil.jokester.ui.theme.JokesterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppBar(
    title: String = "Title",
    description: String = "Description",
    onClick: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
    onClearChat: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(text = title, color = Color.Black)
        },
        navigationIcon = {
            IconButton(onClick = { onBack?.invoke() }) {
                Icon(Icons.Filled.ArrowBack, "backIcon", tint = Color.Black)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White
        ),
        actions = {
            OverflowMenu {
                ChatDropDownMenu(Icons.Filled.Edit, "Edit", onEdit)
                ChatDropDownMenu(Icons.Filled.Delete, "Clear chat history", onClearChat)
            }
        }
    )
}

@Composable
fun OverflowMenu(content: @Composable () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    IconButton(onClick = {
        showMenu = !showMenu
    }) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = "dropdown menu",
        )
    }
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        content()
    }
}

@Composable
fun ChatDropDownMenu(
    icon: ImageVector,
    text: String,
    action: (() -> Unit)?
) {
    DropdownMenuItem(text = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = text)
            Text(text = text, modifier = Modifier.padding(8.dp))
        }
    }, onClick = { action?.invoke() })
}

@Preview
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ChatAppBarPreview() {
    JokesterTheme {
        ChatAppBar()
    }
}