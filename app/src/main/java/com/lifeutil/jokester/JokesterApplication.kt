package com.lifeutil.jokester

import android.app.Application
import android.content.Context
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.lifeutil.jokester.data.db.ChatDatabase
import kotlin.time.Duration.Companion.seconds

class JokesterApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        OpenAIHelper.init("")
    }
}

object OpenAIHelper {
    lateinit var openAI: OpenAI

    fun init(token: String) {
        openAI = OpenAI(
            OpenAIConfig(
                token = token,
                timeout = Timeout(30.seconds)
            )
        )
    }
}

object DBHelper {
    lateinit var chatDatabase: ChatDatabase

    fun init(context: Context) {
        chatDatabase = ChatDatabase.create(context)
    }
}