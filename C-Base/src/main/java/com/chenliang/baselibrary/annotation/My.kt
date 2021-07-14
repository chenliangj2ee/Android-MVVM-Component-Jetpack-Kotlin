package com.chenliang.baselibrary.annotation

import android.util.Log

/**
 *
 */

@Target(AnnotationTarget.CLASS)
annotation class My(
    val myToolbarTitle: String = "",//标题
    val myFullScreen: Boolean = false,//是否全屏
    val myRefresh: Boolean = false//是否启用下拉刷新
)


/**
 * 获取Activity Title
 */
fun activityTitle(cla: Any): String {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(My::class.java) ?: return ""
    return annotation.myToolbarTitle
}


/**
 * 获取Activity 是否显示toolbar
 */
fun activityRefresh(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(My::class.java) ?: return false
    return annotation.myRefresh
}

/**
 * 获取Activity 是否是否全屏
 */
fun activityFullScreen(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(My::class.java) ?: return false
    return annotation.myFullScreen
}