package com.chenliang.baselibrary.annotation

import androidx.databinding.ViewDataBinding

/**
 * loading:是否显示loadingDialog：true显示，false，不显示，默认true
 * cache：是否启用缓存，true启用，false不启用，默认启用
 * hasCacheLoading:存在缓存数据的情况下，是否还显示loading
 */
@Target(AnnotationTarget.CLASS)
annotation class MVVM(
    val id: Int = 0
)

//获取类上面的注解
fun layoutId(cla: Any): Int {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MVVM::class.java) ?: return 0
    return annotation.id
}