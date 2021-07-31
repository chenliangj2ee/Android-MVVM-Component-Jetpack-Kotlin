package com.chenliang.baselibrary.utils

import android.content.Context
import com.chenliang.baselibrary.R

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.utils
 * @author: chenliang
 * @date: 2021/7/12
 */
object MyKotlinClass {
    fun <T> createByName(name: String?): T? {
        try {
            return Class.forName(name).newInstance() as T
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getLayoutIdByBinding(
        context: Context,
        bingingName: String
    ): Int {
        var bingingName = bingingName
        bingingName = bingingName.replace("([a-z])([A-Z])".toRegex(), "$1_$2").toLowerCase()
        val bingingNames =
            bingingName.split("\\.".toRegex()).toTypedArray()
        val name = bingingNames[bingingNames.size - 1].replace("_binding", "")
        var id=context.resources.getIdentifier(name, "layout", context.packageName)
//        log("解析$bingingName---->name: $name    id:$id")
        return id
    }
}