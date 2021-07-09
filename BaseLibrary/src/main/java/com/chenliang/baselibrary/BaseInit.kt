package com.chenliang.baselibrary

import android.content.Context
import com.chenliang.baselibrary.net.utils.MyApiReflex

object BaseInit {
    var con: Context? = null

    fun   registerApi(context: Context, vararg classes: Class<*>) {
        con = context

        for (item in classes) {
            MyApiReflex.register(item)
        }

    }

}