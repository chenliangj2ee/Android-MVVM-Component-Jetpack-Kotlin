package com.chenliang.annotation

/**
 *配置生成器注解
 */

@Target(AnnotationTarget.CLASS)
annotation class MyRoute(
    val mPath: String = ""
)

