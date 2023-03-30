package com.lifeutil.jokester

sealed class Route(val route: String) {
    object Chat : Route("chat_screen/{conversationId}") {
        fun createRoute(conversationId: Long) = "chat_screen/$conversationId"
    }
    object ChatList: Route("chat_list_screen")
}