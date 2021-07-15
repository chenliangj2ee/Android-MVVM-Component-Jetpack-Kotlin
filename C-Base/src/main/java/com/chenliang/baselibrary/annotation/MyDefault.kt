package com.chenliang.baselibrary.annotation

import com.chenliang.baselibrary.base.MyBaseBean
import com.chenliang.baselibrary.utils.anrCheck
import com.chenliang.baselibrary.utils.log

/**
 *
 */

@Target(AnnotationTarget.FIELD)
annotation class MyDefault(
    val myValue: String = ""

)

/**
 * 获取默认值
 */
fun defaultValue(cla: Any): String {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyDefault::class.java) ?: return ""
    return annotation.myValue
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
        if (String::class.java.name == fieldType.name) {

            if (fieldValue == null) {
                var myDefault = f.getAnnotation(MyDefault::class.java)
                if (myDefault?.myValue != null) {

                    f.set(any, myDefault?.myValue)
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