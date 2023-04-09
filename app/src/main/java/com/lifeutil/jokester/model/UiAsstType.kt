package com.lifeutil.jokester.model

import androidx.compose.ui.graphics.vector.ImageVector

data class UiAsstType(
    val type: AsstType,
    val title: String,
    val hint: String,
    val icon: ImageVector,
    val systemMessage: String
)