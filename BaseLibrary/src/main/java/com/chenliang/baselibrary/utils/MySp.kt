package com.chenliang.baselibrary.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.chenliang.baselibrary.BaseInit
import java.lang.reflect.Type
import java.util.*

internal object MySp {
    private var MyCache = "MySp-android"

    fun putString(
        context: Context,
        key: String,
        value: String
    ) {
        Log.i("MySp", "put key:$key  value:$value")
        val sp =
            context.getSharedPreferences(MyCache, Context.MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString(key, value)
        edit.commit()
    }

    fun getString(context: Context, key: String): String? {
        val sp =
            context.getSharedPreferences(MyCache, Context.MODE_PRIVATE)
        val result = sp.getString(key, "")
        Log.i("MySp", "get key:$key  value:$result")
        return result
    }

    fun clear(context: Context, key: String) {
        val sp =
            context.getSharedPreferences(MyCache, Context.MODE_PRIVATE)
        val result = sp.edit().remove(key)
    }

    fun <T> getObject(
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

    fun <T> getObjects(
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