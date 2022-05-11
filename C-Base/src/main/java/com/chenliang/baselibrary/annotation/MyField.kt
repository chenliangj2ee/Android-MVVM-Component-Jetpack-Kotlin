package com.chenliang.baselibrary.annotation

import android.app.Activity
import androidx.fragment.app.Fragment
import com.chenliang.baselibrary.utils.log
import java.io.Serializable

/**
 *
 */

@Target(AnnotationTarget.FIELD)
annotation class MyField(
    val mKey: String = ""
)

/**
 * 获取注解Key
 */
fun myIntentKey(cla: Any): String {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyField::class.java) ?: return ""
    return annotation.mKey
}


fun initValueFromIntent(any: Any) {
    try {

        if (any is Activity || any is Fragment) {
            var fields = any::class.java.declaredFields

            for (f in fields) {
                f.isAccessible = true
                var fieldName = f.name
                var fieldType = f.type
                var anno = f.getAnnotation(MyField::class.java)
                if (anno != null) {
                    var intentKey = fieldName
                    if (anno?.mKey != "") {
                        intentKey = anno?.mKey
                    }

                    if (any is Activity) {
                        when {
                            String::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.intent.getStringExtra(intentKey) ?: "${f.get(any)}")
//                                any.log("传参${intentKey}：${any.intent.getStringExtra(intentKey)}")
                            }

                            Int::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.intent.getIntExtra(intentKey, f.getInt(any)))
                                any.log("传参${intentKey}：${any.intent.getIntExtra(intentKey, 0)}")
                            }

                            Boolean::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.intent.getBooleanExtra(intentKey, f.getBoolean(any)))
//                                any.log("传参${intentKey}：${any.intent.getBooleanExtra(intentKey, false)}")
                            }

                            Long::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.intent.getLongExtra(intentKey, f.getLong(any)))
//                                any.log("传参${intentKey}：${any.intent.getLongExtra(intentKey, 0)}")
                            }

                            Double::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.intent.getDoubleExtra(intentKey, f.getDouble(any)))
//                                any.log("传参${intentKey}：${ any.intent.getDoubleExtra(intentKey, 0.0)}")
                            }

                            Float::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.intent.getFloatExtra(intentKey, f.getFloat(any)))
//                                any.log("传参${intentKey}：${ any.intent.getFloatExtra(intentKey, 0F)}")
                            }

                            Serializable::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.intent.getSerializableExtra(intentKey))
//                                any.log("传参${intentKey}：${ any.intent.getSerializableExtra(intentKey)?.toJson()}")
                            }

                        }

                    }
                    if (any is Fragment) {
                        when {
                            String::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.arguments?.getString(intentKey)?: "${f.get(any)}")
//                                any.log("传参${intentKey}：${any.arguments?.getString(intentKey)}")
                            }

                            Int::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.arguments?.getInt(intentKey, f.getInt(any)))
//                                any.log("传参${intentKey}：${any.arguments?.getInt(intentKey, 0)}")
                            }

                            Boolean::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.arguments?.getBoolean(intentKey, f.getBoolean(any)))
//                                any.log("传参${intentKey}：${any.arguments?.getBoolean(intentKey, false)}")
                            }

                            Long::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.arguments?.getLong(intentKey, f.getLong(any)))
//                                any.log("传参${intentKey}：${any.arguments?.getLong(intentKey, 0)}")
                            }

                            Double::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.arguments?.getDouble(intentKey, f.getDouble(any)))
//                                any.log("传参${intentKey}：${ any.arguments?.getDouble(intentKey, 0.0)}")
                            }

                            Float::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.arguments?.getFloat(intentKey, f.getFloat(any)))
//                                any.log("传参${intentKey}：${ any.arguments?.getFloat(intentKey, 0F)}")
                            }

                            Serializable::class.java.isAssignableFrom(fieldType) -> {
                                f.set(any, any.arguments?.getSerializable(intentKey))
//                                any.log("传参${intentKey}：${ any.arguments?.getSerializable(intentKey)?.toJson()}")
                            }
                        }

                    }

                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

}
