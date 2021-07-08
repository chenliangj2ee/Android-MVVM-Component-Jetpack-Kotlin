package com.chenliang.baselibrary

import android.content.Context
import com.chenliang.baselibrary.net.utils.MyApiReflex

object BaseInit {
    var con: Context? = null

    //    fun <T> registerApi(context: Context, vararg classes: Class<T>) {
//        con = context
//
//        for (item in classes) {
//            MyApiAnno.register(item)
//        }
//
//    }
    fun <T> registerApi(context: Context, classes: Class<T>) {
        con = context
        MyApiReflex.register(classes)

    }
}