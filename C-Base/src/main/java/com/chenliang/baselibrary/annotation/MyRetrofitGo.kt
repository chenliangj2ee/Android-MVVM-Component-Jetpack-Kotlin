package com.chenliang.baselibrary.annotation

/**
 * myLoading:是否显示loadingDialog：true显示，false，不显示，默认false
 * myCache：是否启用缓存，true启用，false不启用，，默认false
 * myHasCacheLoading:存在缓存数据的情况下，是否还显示loading,默认false
 * myTag:添加标签，查看log使用,默认显示path
 * myFailToast:失败状态下，是否启用自动Toast显示message，默认false
 * mySuccessCode:接口请求成功，发送的RxBus code,默认值0时不发送;使用场景，
 *              比如,一个添加功能，添加成功后，需要通知列表界面刷新；可配置该注解
 * myFailCode:接口请求失败，发送的RxBus code,默认值0，不发送
 */
@Target(AnnotationTarget.FUNCTION)
annotation class MyRetrofitGo(
    val myLoading: Boolean = false,
    val myCache: Boolean = false,
    val myHasCacheLoading: Boolean = false,
    val myTag: String = "",
    val myFailToast: Boolean = false,
    val mySuccessCode: Int = 0,
    val myFailCode: Int = 0
)