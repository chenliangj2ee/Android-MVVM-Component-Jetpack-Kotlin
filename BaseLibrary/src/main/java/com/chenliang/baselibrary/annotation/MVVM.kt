package com.chenliang.baselibrary.annotation

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

/**
 *
 */

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.BINARY)
annotation class MVVM(
    @LayoutRes val id: Int=0
)

/**
 * 获取Activity、fragment、dialog对应的布局id
 */
fun layoutId(cla: Any): Int {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MVVM::class.java) ?: return 0
    return annotation.id
}