package com.lifeutil.jokester.model

data class ChatMessage(
    val id: Long,
    val message: String,
    val date: Long,
    val fromMe: Boolean = true
)