package com.lifeutil.jokester

import android.app.Application
import com.aallam.openai.client.OpenAI

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