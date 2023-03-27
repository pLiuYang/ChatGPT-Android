package com.lifeutil.jokester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lifeutil.jokester.ui.chat.ChatScreen
import com.lifeutil.jokester.ui.chatlist.ChatListScreen
import com.lifeutil.jokester.ui.theme.JokesterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JokesterTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "chat_list_screen") {
                    composable("chat_list_screen") {
                        ChatListScreen(
                            onNavigateToChat = { navController.navigate("chat_screen") }
                        )
                    }
                    composable("chat_screen") { ChatScreen() }
                }

//                Surface(color = MaterialTheme.colorScheme.background) {
//                    Column(modifier = Modifier.fillMaxSize()) {
//                        ChatScreen()
//                    }
//                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JokesterTheme {
        ChatListScreen {}
    }
}