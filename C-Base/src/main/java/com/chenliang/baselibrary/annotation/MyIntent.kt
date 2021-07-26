package com.chenliang.baselibrary.annotation

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.chenliang.baselibrary.base.MyBaseBean
import com.chenliang.baselibrary.utils.anrCheck
import com.chenliang.baselibrary.utils.log
import java.io.Serializable

/**
 *
 */

@Target(AnnotationTarget.FIELD)
annotation class MyIntent(
    val myKey: String = ""
)

/**
 * 获取注解Key
 */
fun myIntentKey(cla: Any): String {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyIntent::class.java) ?: return ""
    return annotation.myKey
}


fun initValueFromIntent(any: Any) {

    if (any is Activity || any is Fragment) {
        var fields = any::class.java.declaredFields

        for (f in fields) {
            f.isAccessible = true
            var fieldValue = f.get(any)

            if (fieldValue == null) {
                var fieldName = f.name
                var fieldType = f.type
                var anno = f.getAnnotation(MyIntent::class.java)
                var intentKey = fieldName
                if (anno?.myKey != "") {
                    intentKey = anno?.myKey
                }

                if (any is Activity) {
                    when (fieldType.name) {
                        String::class.java.name -> {
                            f.set(any, any.intent.getStringExtra(intentKey))
                        }
                        Int::class.java.name -> {
                            f.set(any, any.intent.getIntExtra(intentKey, 0))
                        }
                        Boolean::class.java.name -> {
                            f.set(any, any.intent.getBooleanExtra(intentKey, false))
                        }
                        Long::class.java.name -> {
                            f.set(any, any.intent.getLongExtra(intentKey, 0))
                        }
                        Double::class.java.name -> {
                            f.set(any, any.intent.getDoubleExtra(intentKey, 0.0))
                        }
                        Float::class.java.name -> {
                            f.set(any, any.intent.getFloatExtra(intentKey, 0F))
                        }
                        is Serializable -> {
                            f.set(any, any.intent.getSerializableExtra(intentKey))
                        }
                    }

                }
                if (any is Fragment) {
                    when (fieldType.name) {
                        String::class.java.name -> {
                            f.set(any, any.arguments?.getString(intentKey))
                        }
                        Int::class.java.name -> {
                            f.set(any, any.arguments?.getInt(intentKey, 0))
                        }
                        Boolean::class.java.name -> {
                            f.set(any, any.arguments?.getBoolean(intentKey, false))
                        }
                        Long::class.java.name -> {
                            f.set(any, any.arguments?.getLong(intentKey, 0))
                        }
                        Double::class.java.name -> {
                            f.set(any, any.arguments?.getDouble(intentKey, 0.0))
                        }
                        Float::class.java.name -> {
                            f.set(any, any.arguments?.getFloat(intentKey, 0F))
                        }
                        is Serializable -> {
                            f.set(any, any.arguments?.getSerializable(intentKey))
                        }
                    }

                }


            }
        }
    }

}
