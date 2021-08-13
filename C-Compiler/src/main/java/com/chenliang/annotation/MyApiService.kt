package com.chenliang.annotation

/**
 *配置生成器注解
 */

@Target(AnnotationTarget.CLASS)
annotation class MyApiService(
    val mName: String = "",//API接口名称
    val mPath: String = "",//API接口生产环境base路径
    val mDevPath: String = "",//API接口开发环境base路径
    val mTestPath: String = ""//API接口测试环境base路径
)

object ApiModel {

    var test = false
    var dev = false
    var release = true

    fun openTest() {
        test = true
        release = false
        dev = false
    }

    fun openDev() {
        dev = true
        release = false
        test = false
    }

    fun openRelease() {
        release = true
        test = false
        dev = false
    }
}