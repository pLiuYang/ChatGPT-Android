package com.lifeutil.jokester.model

/**
 * UI state for chat screen
 */
sealed class ChatUiState {
    data class Success(val messages: List<UiChatMessage>) : ChatUiState()
    object Loading : ChatUiState()
}