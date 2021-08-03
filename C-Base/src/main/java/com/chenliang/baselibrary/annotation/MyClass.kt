package com.chenliang.baselibrary.annotation

import android.view.Gravity


/**
 *
 * @property myToolbarTitle String
 * @property myFullScreen Boolean
 * @property myShowNetworkError Boolean
 * @property myRefresh Boolean
 * @property myDialogGravity Int
 * @property myDialogAnimation Boolean
 * @property myDialogAnimationTime Long
 * @property myDialogTransparent Boolean
 * @constructor
 */
@Target(AnnotationTarget.CLASS)
annotation class MyClass(
    val myToolbarTitle: String = "",//Activity fragment标题
    val myFullScreen: Boolean = false,//Activity是否全屏
    val myShowNetworkError: Boolean = false,//是否网络异常时，Activity，fragmen显示View提示
    val myRefresh: Boolean = false,//Activity fragment是否启用下拉刷新
    val myDialogGravity: Int = Gravity.CENTER,//dialog默认居中显示
    val myDialogAnimation: Boolean = false,//dialog默认居中显示
    val myDialogAnimationTime: Long = 400,//dialog默认居中显示
    val myDialogTransparent: Boolean = false//dialog是否透明


)


/**
 * 获取Activity Title
 */
fun activityTitle(cla: Any): String {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return ""
    return annotation.myToolbarTitle
}


/**
 * 获取Activity 是否显示toolbar
 */
fun activityRefresh(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return false
    return annotation.myRefresh
}

/**
 * 获取Activity 是否是否全屏
 */
fun activityFullScreen(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return false
    return annotation.myFullScreen
}

/**
 * 获取Dialog显示位置
 */
fun dialogGravity(cla: Any): Int {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return Gravity.CENTER
    return annotation.myDialogGravity
}

/**
 * 获取Dialog是否透明
 */
fun dialogTransparent(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return false
    return annotation.myDialogTransparent
}

/**
 * 获取Dialog是否启用动画
 */
fun myDialogAnimation(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return false
    return annotation.myDialogAnimation
}

/**
 * 获取Dialog动画时长
 */
fun myDialogAnimationTime(cla: Any): Long {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return 400
    return annotation.myDialogAnimationTime
}

/**
 * 获取Activity是否显示网络异常提示
 */
fun myShowNetworkError(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return false
    return annotation.myShowNetworkError
}