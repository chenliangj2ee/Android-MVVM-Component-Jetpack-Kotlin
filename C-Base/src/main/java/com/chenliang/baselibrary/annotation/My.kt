package com.chenliang.baselibrary.annotation

import android.view.Gravity

/**
 *
 */

@Target(AnnotationTarget.CLASS)
annotation class My(
    val myToolbarTitle: String = "",//标题
    val myFullScreen: Boolean = false,//是否全屏
    val myRefresh: Boolean = false,//是否启用下拉刷新
    val myDialogGravity: Int = Gravity.CENTER,//dialog默认居中显示
    val myDialogTransparent: Boolean = false

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

/**
 * 获取Dialog显示位置
 */
fun dialogGravity(cla: Any): Int {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(My::class.java) ?: return Gravity.CENTER
    return annotation.myDialogGravity
}

/**
 * 获取Dialog是否透明
 */
fun dialogTransparent(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(My::class.java) ?: return false
    return annotation.myDialogTransparent
}