package com.chenliang.baselibrary.net.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.utils.log
import java.lang.reflect.Type
import java.util.*

internal object MyHttpDB {
    private var MyCache = "SPUtils-android"
    private val datasMap = HashMap<String, Any>()

    private fun putString(
        context: Context,
        key: String,
        value: String
    ) {
        log("put key:$key  value:$value")
        val sp =
            context.getSharedPreferences(MyCache, Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString(key, value)
        edit.commit()
    }

    private fun getString(context: Context, key: String): String? {
        val sp =
            context.getSharedPreferences(MyCache, Context.MODE_PRIVATE)
        val result = sp.getString(key, "")
        log("get key:$key  value:$result")
        return result
    }

    fun <T> getCache(
        key: String,
        clazz: Class<T>?
    ): T? {

        var res = datasMap[key]
        if (res != null) {
            log("使用内存缓存")
            return res as T
        }

        res = BaseInit.con?.let {
            getObject(
                it,
                key,
                clazz
            )
        }
        if (res != null) {
            log("使用文件缓存")
            datasMap[key] = res
        }
        return res
    }


    fun putCache(
        key: String?,
        bean: Any?
    ) {
        if (BaseInit.con == null || bean == null || key == null)
            return
        datasMap[key] = bean
        log("更新数据到内存缓存")
        if (datasMap.size > 100)
            datasMap.clear()
        putString(
            BaseInit.con!!,
            key,
            Gson().toJson(bean)
        )
        log("更新数据到文件缓存")
    }


    private fun <T> getObject(
        context: Context,
        key: String,
        clazz: Class<T>?
    ): T? {
        if (context == null)
            return null
        val json =
            getString(context, key)
        return if (TextUtils.isEmpty(json)) {
            null
        } else try {
            Gson().fromJson<T>(json, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun <T> getObjects(
        context: Context,
        key: String,
        type: Type?
    ): ArrayList<T>? {
        val json =
            getString(context, key)
        return if (TextUtils.isEmpty(json)) {
            null
        } else try {
            Gson().fromJson<ArrayList<T>>(json, type)
        } catch (e: Exception) {
            null
        }
    }
}