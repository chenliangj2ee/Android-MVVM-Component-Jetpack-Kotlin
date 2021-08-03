package com.chenliang.baselibrary

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.chenliang.annotation.MyRouteUtils
import com.chenliang.baselibrary.net.utils.MyApiReflex
import kotlin.concurrent.thread

@SuppressLint("StaticFieldLeak")
object BaseInit {
    var con: Context? = null
    var isTest: Boolean =BuildConfig.CONFIG_TEST

    fun init(context: Application) {
        if (con == null) {
            con = context
        }

    }

    fun registerApi(vararg classes: Class<*>) {
        thread {
            for (item in classes) {
                MyApiReflex.register(item)
            }
        }

    }

    fun initMyRoute(any: Any) {
        var fields = any::class.java.declaredFields
        fields.forEach {
            it.isAccessible = true
            var name = it.name
            var value = it.get(any)
            if (value is String) {
                var key=value.split("|")[0]
                MyRouteUtils.path[key] = value
                Log.i("MyRouteUtils", "$key  :  $value")
            }

        }
    }

}