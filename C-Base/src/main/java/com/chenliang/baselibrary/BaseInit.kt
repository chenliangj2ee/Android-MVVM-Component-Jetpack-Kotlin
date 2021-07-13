package com.chenliang.baselibrary

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.chenliang.baselibrary.net.utils.MyApiReflex
import com.chenliang.baselibrary.utils.log
import kotlin.concurrent.thread

object BaseInit {
    var con: Context? = null

    fun init(context: Application) {
        if (con == null) {
            con = context
            thread {
                ARouter.openDebug();
                ARouter.init(context);
            }
        }

    }

    fun registerApi(vararg classes: Class<*>) {
        for (item in classes) {
            MyApiReflex.register(item)
        }

    }

}