package com.chenliang.annotation

/**
 *配置生成器注解
 */

@Target(AnnotationTarget.CLASS)
annotation class MyApiService(
    val mName: String = "",//API接口名称
    val mPath: String = ""//API接口base路径
)

