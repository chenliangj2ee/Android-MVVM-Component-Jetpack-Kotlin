package com.chenliang.baselibrary.extend

import android.util.Log
import android.view.View
import android.widget.Toast
import com.chenliang.baselibrary.BaseInit

fun Any.hasNull(vararg args: String): Boolean {
    val array = arrayOf(args)
    var size = args.size - 1

    for (index in 0..size step 2) {
        var item = args[index]
        var message = args[index + 1]

        if (item == null) {
            toast(message)
            return true
        }

        when (item) {
            is String -> {
                if (item.isEmpty()) {
                    toast(message)
                    return true
                }
            }
        }
    }
    return false
}

fun Any.toast(message: String) {
    Toast.makeText(BaseInit.con, message, Toast.LENGTH_LONG).show()
}

