package com.chenliang.baselibrary.annotation

import com.chenliang.baselibrary.base.MyBaseBean

/**
 *
 */

@Target(AnnotationTarget.FIELD)
annotation class MyDefault(
    val mValue: String = ""
)

/**
 * 获取默认值
 */
fun defaultValue(cla: Any): String {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyDefault::class.java) ?: return ""
    return annotation.mValue
}

/**
 * 获取默认值
 */
fun defaultValueReflex(any: Any) {
    var fields = any::class.java.declaredFields

    for (f in fields) {

        f.isAccessible = true
        var fieldValue = f.get(any)
        var fieldType = f.type
        if (String::class.java.isAssignableFrom(fieldType)) {
            if (fieldValue == null) {
                var myDefault = f.getAnnotation(MyDefault::class.java)
                if (myDefault?.mValue != null) {
                    f.set(any, myDefault?.mValue)
                }
            }
        } else if (fieldValue is MyBaseBean) {
            defaultValueReflex(fieldValue)
        } else if (fieldValue is List<*>) {
            for (item in fieldValue) {
                if (item != null) {
                    defaultValueReflex(item)
                }
            }
        }
    }

}