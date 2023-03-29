package com.lifeutil.jokester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope.SlideDirection
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.lifeutil.jokester.ui.chat.ChatScreen
import com.lifeutil.jokester.ui.chatlist.ChatListScreen
import com.lifeutil.jokester.ui.theme.JokesterTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokesterTheme {
                val navController = rememberAnimatedNavController()
                AnimatedNavHost(
                    navController = navController,
                    startDestination = Route.ChatList.route
                ) {
                    composable(
                        Route.ChatList.route,
                    ) {
                        ChatListScreen(
                            onNavigateToChat = { navController.navigate(Route.Chat.route) }
                        )
                    }
                    composable(
                        Route.Chat.route,
                        enterTransition = {
                            slideIntoContainer(SlideDirection.Left, animationSpec = tween(700))
                        },
                        exitTransition = {
                            slideOutOfContainer(SlideDirection.Left, animationSpec = tween(700))
                        },
                        popEnterTransition = {
                            slideIntoContainer(SlideDirection.Right, animationSpec = tween(700))
                        },
                        popExitTransition = {
                            slideOutOfContainer(SlideDirection.Right, animationSpec = tween(700))
                        }
                    ) {
                        ChatScreen(onBack = { navController.popBackStack() })
                    }
                }
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