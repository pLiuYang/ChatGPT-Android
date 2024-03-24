package com.lifeutil.jokester.ui.util

import android.content.SharedPreferences

object SPUtil {

    const val COINS_COUNT = "coins_count"
    var sharedPref: SharedPreferences? = null

    fun getRemainCoins(): Int {
        return sharedPref?.getInt(COINS_COUNT, 1000) ?: 0
    }

    fun costCoins(num: Int) {
        val cur = sharedPref?.getInt(COINS_COUNT, 1000) ?: num
        sharedPref?.edit()?.apply {
            putInt(COINS_COUNT, cur - num)
            apply()
        }
    }
}