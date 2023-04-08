package com.lifeutil.jokester.model

/**
 * Type of AI assistant. Affects the AI system message.
 */
enum class AsstType(val value: Int) {
    DEFAULT(0),
    TRANSLATOR(1),
    MATH(2);

    companion object {
        fun fromInt(value: Int) = AsstType.values().firstOrNull { it.value == value } ?: DEFAULT
    }
}