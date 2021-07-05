package com.chenliang.baselibrary.annotation

import androidx.databinding.ViewDataBinding
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

/**
 *
 */

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class MVVM(
    val id: Int = 0
)

/**
 * 获取Activity、fragment、dialog对应的布局id
 */
fun layoutId(cla: Any): Int {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MVVM::class.java) ?: return 0
    return annotation.id
}