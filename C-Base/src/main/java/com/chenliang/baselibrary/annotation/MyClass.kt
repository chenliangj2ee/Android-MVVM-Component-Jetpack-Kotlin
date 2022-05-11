package com.chenliang.baselibrary.annotation

import android.view.Gravity


/**
 *
 * @property mToolbarTitle String
 * @property mFullScreen Boolean
 * @property mShowNetworkError Boolean
 * @property mRefresh Boolean
 * @property mDialogGravity Int
 * @property mDialogAnimation Boolean
 * @property mDialogAnimationTime Long
 * @property mDialogTransparent Boolean
 * @constructor
 */
@Target(AnnotationTarget.CLASS)
annotation class MyClass(
    val mToolbarTitle: String = "",//Activity fragment标题
    val mFullScreen: Boolean = false,//Activity是否全屏
    val mShowNetworkError: Boolean = false,//是否网络异常时，Activity，fragmen显示View提示
    val mRefresh: Boolean = false,//Activity fragment是否启用下拉刷新
    val mScroll: Boolean = false,//Activity fragment
    val mDialogGravity: Int = Gravity.CENTER,//dialog默认居中显示
    val mDialogAnimation: Boolean = false,//dialog默认居中显示
    val mDialogAnimationTime: Long = 300,//dialog默认动画时长
    val mDialogTransparent: Boolean = false//dialog是否透明


)


/**
 * 获取Activity Title
 */
fun activityTitle(cla: Any): String {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return ""
    return annotation.mToolbarTitle
}


/**
 * 获取Activity 是否显示toolbar
 */
fun activityRefresh(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return false
    return annotation.mRefresh
}

/**
 * 获取Activity 是否启动scroll
 */
fun isScroll(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return false
    return annotation.mScroll
}

/**
 * 获取Activity 是否是否全屏
 */
fun activityFullScreen(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return false
    return annotation.mFullScreen
}

/**
 * 获取Dialog显示位置
 */
fun dialogGravity(cla: Any): Int {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return Gravity.CENTER
    return annotation.mDialogGravity
}

/**
 * 获取Dialog是否透明
 */
fun dialogTransparent(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return false
    return annotation.mDialogTransparent
}

/**
 * 获取Dialog是否启用动画
 */
fun myDialogAnimation(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return false
    return annotation.mDialogAnimation
}

/**
 * 获取Dialog动画时长
 */
fun myDialogAnimationTime(cla: Any): Long {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return 400
    return annotation.mDialogAnimationTime
}

/**
 * 获取Activity是否显示网络异常提示
 */
fun myShowNetworkError(cla: Any): Boolean {
    val clazz = cla::class.java
    val annotation = clazz.getAnnotation(MyClass::class.java) ?: return false
    return annotation.mShowNetworkError
}