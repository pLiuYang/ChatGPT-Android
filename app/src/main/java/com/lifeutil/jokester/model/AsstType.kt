package com.lifeutil.jokester.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Translate

/**
 * Type of AI assistant. Affects the AI system message.
 */
enum class AsstType(val value: Int) {
    DEFAULT(0),
    TRANSLATOR(1),
    EN_DICTIONARY(2),
    TRAVEL_PLANNER(3),
    MATH(4);

    companion object {
        fun fromInt(value: Int) = AsstType.values().firstOrNull { it.value == value } ?: DEFAULT

        fun getSystemMessage(asstType: AsstType): UiAsstType {
            return when (asstType) {
                DEFAULT -> UiAsstType(
                    DEFAULT,
                    "General topic",
                    "Ask anything",
                    Icons.Filled.Info,
                    ""
                )
                TRANSLATOR -> UiAsstType(
                    TRANSLATOR,
                    "Translator",
                    "Translate from any language into English",
                    Icons.Filled.Translate,
                    "You are an any language to English translator"
                )
                EN_DICTIONARY -> UiAsstType(
                    EN_DICTIONARY,
                    "Dictionary",
                    "Show the English explanation with samples",
                    Icons.Filled.LibraryBooks,
                    "Display explanation in English and samples usages"
                )
                TRAVEL_PLANNER -> UiAsstType(
                    TRAVEL_PLANNER,
                    "Travel planner",
                    "Ask anything about traveling",
                    Icons.Filled.FlightTakeoff,
                    "Travel assistant"
                )
                MATH -> UiAsstType(
                    MATH,
                    "Math tutor",
                    "Answer your questions about Math",
                    Icons.Filled.Calculate,
                    "Solve math equations"
                )
            }
        }
    }
}