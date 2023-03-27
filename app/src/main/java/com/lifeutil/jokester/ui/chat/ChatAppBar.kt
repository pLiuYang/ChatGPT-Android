package com.lifeutil.jokester.ui.chat

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.lifeutil.jokester.ui.theme.JokesterTheme
import com.lifeutil.jokester.ui.theme.Teal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppBar(
    title: String = "Title",
    description: String = "Description",
    onClick: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { onBack?.invoke() }) {
                Icon(Icons.Filled.ArrowBack, "backIcon", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Teal,
            titleContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = { onEdit?.invoke() }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit chat", tint = Color.White)
            }
        }
    )
}

@Preview
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ChatAppBarPreview() {
    JokesterTheme {
        ChatAppBar()
    }
}