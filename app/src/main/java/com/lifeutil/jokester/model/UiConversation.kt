package com.lifeutil.jokester.model

data class UiConversation(
    val id: Long,
    val topic: String,
    val asstType: AsstType,
    val lastMessage: String,
    val lastUpdated: CharSequence
)