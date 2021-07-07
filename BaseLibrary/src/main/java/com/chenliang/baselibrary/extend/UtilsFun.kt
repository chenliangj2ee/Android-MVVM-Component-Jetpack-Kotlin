package com.chenliang.baselibrary.extend

import android.view.View

fun Any.hasNull(vararg args: Any): Boolean {
    for (item in args) {
        if (item == null)
            return true
        when (item) {
            is String -> if (item.length == 0) return true
        }
    }
    return false
}
