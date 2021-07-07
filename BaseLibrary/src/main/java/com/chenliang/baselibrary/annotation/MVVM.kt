package com.chenliang.baselibrary.annotation

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

/**
 *
 */

@Target(AnnotationTarget.CLASS)
annotation class MVVM(
    val id: Int = 0,
    val title: String = ""
)

/**
 * 获取Activity、fragment、dialog对应的布局id
 */
fun layoutId(cla: Any): Int {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MVVM::class.java) ?: return 0
    return annotation.id
}

/**
 * 获取Activity Title
 */
fun activityTitle(cla: Any): String {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MVVM::class.java)

    return annotation.title
}