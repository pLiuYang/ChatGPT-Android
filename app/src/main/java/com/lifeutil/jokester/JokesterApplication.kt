package com.lifeutil.jokester

import android.app.Application
import android.content.Context
import com.aallam.openai.client.OpenAI
import com.lifeutil.jokester.data.db.ChatDatabase

class JokesterApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        OpenAIHelper.init("")
    }
}

object OpenAIHelper {
    lateinit var openAI: OpenAI

    fun init(token: String) {
        openAI = OpenAI(token)
    }
}

object DBHelper {
    lateinit var chatDatabase: ChatDatabase

    fun init(context: Context) {
        chatDatabase = ChatDatabase.create(context)
    }
}