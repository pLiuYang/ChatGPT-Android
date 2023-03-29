package com.lifeutil.jokester

sealed class Route(val route: String) {
    object Chat : Route("chat_screen")
    object ChatList: Route("chat_list_screen")
}