package com.lifeutil.jokester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.lifeutil.jokester.ui.chat.ChatScreen
import com.lifeutil.jokester.ui.theme.JokesterTheme
import com.lifeutil.jokester.ui.theme.Teal

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JokesterTheme {
                SetStatusBarColor(color = Teal)

                Surface(color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        ChatScreen()
                    }
                }
            }
        }
    }
}

@Composable
private fun SetStatusBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JokesterTheme {
        ChatScreen()
    }
}