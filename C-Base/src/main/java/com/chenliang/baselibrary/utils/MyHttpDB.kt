package com.chenliang.baselibrary.utils

import android.content.Context
import android.text.TextUtils
import com.chenliang.baselibrary.BaseInit
import com.google.gson.Gson
import java.lang.reflect.Type
import java.util.*

/**
 * retrofit 缓存
 */
internal object MyHttpDB {
    private var MyCache = "MyHttpDB"
    private val datasMap = HashMap<String, String>()

    private fun putString(
        context: Context,
        key: String,
        value: String
    ) {
        val sp =
            context.getSharedPreferences(MyCache, Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString(key, value)
        edit.commit()

        if (value.contains(" \"records\" : []")) {
            clearCache(key)
        }
    }

    private fun getString(context: Context, key: String): String? {
        val sp =
            context.getSharedPreferences(MyCache, Context.MODE_PRIVATE)
        val result = sp.getString(key, "")
        return result
    }

    fun getCacheJson(
        key: String,
    ): String? {

        var res = datasMap[key]
        if (res != null) {
            return res
        }

        res = BaseInit.con?.let { getString(it, key) }
        if (res != null) {
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
        datasMap[key] = bean.toJson()
        if (datasMap.size > 100)
            datasMap.clear()
        putString(
            BaseInit.con!!,
            key,
            Gson().toJson(bean)
        )
    }

    fun clearCache(
        key: String?
    ) {
        if (BaseInit.con == null || key == null)
            return
        datasMap.remove(key)
        putString(
            BaseInit.con!!,
            key,
            ""
        )
    }

    fun clearAll() {
        val sp =
            BaseInit.con!!.getSharedPreferences(MyCache, Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.clear()
        edit.commit()
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