package com.lifeutil.jokester.model

data class UiChatMessage(
    val id: Long,
    val message: String,
    val lastUpdated: Long,
    val fromMe: Boolean = true,
    val isLoading: Boolean = false
)