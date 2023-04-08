package com.lifeutil.jokester.data

object DataConstants {

    private val emojiSet by lazy { setOf(
        "\uD83C\uDF66", // 🍦 soft ice cream
        "\uD83C\uDF67", // 🍧 shaved ice
        "\uD83C\uDF68", // 🍨 ice cream
        "\uD83C\uDF69", // 🍩 doughnut
        "\uD83C\uDF6A", // 🍪 cookie
        "\uD83C\uDF82", // 🎂 birthday cake
        "\uD83C\uDF70", // 🍰 shortcake
        "\uD83E\uDDC1", // 🧁 cupcake
        "\uD83C\uDF6B", // 🍫 chocolate bar
        "\uD83C\uDF6C", // 🍬 candy
        "\uD83C\uDF6D", // 🍭 lollipop
        "\uD83C\uDF6E", // 🍮 custard
        "\uD83C\uDF6F", // 🍯 honey pot
//        "\uD83E\uDD78", // 🥸
//        "\uD83E\uDDD1\u200D\uD83C\uDF93", // 🧑‍🎓
//        "\uD83E\uDDD1\u200D\uD83C\uDF3E", // 🧑‍🌾
//        "\uD83D\uDC69\u200D\uD83C\uDFED", // 👩‍🏭
//        "\uD83D\uDC68\u200D\uD83D\uDCBB", // 👨‍💻
//        "\uD83D\uDC69\u200D\uD83C\uDFA8", // 👩‍🎨
//        "\uD83E\uDDD1\u200D\uD83D\uDE80", // 🧑‍🚀
//        "\uD83D\uDD75", // 🕵 detective
//        "\uD83E\uDD77", // 🥷ninja
//        "\uD83C\uDF85", // 🎅 santa claus
//        "\uD83E\uDDD9", // 🧙 mage
//        "\uD83D\uDEB6", // 🚶 person walking
//        "\uD83C\uDFC3", // 🏃 person running
    ) }

    internal fun getRandomEmoji(): String {
        return emojiSet.random()
    }
}