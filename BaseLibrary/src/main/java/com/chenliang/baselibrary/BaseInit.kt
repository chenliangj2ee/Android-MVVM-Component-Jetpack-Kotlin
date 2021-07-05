package com.chenliang.baselibrary

import android.content.Context
import com.chenliang.baselibrary.net.MyApiAnno

object BaseInit {
    var con: Context? = null

    fun <T> register(context: Context,cla: Class<T>) {
        con = context
        MyApiAnno.register(cla)
    }
}