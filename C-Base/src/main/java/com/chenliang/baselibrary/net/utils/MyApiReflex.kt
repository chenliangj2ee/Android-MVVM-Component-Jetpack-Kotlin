package com.chenliang.baselibrary.net.utils

import com.chenliang.baselibrary.annotation.MyRetrofitGo
import com.chenliang.baselibrary.annotation.MyRetrofitGoValue
import retrofit2.http.GET
import retrofit2.http.POST

object MyApiReflex {

    var value = HashMap<String, MyRetrofitGoValue>()


    fun <T> register(classes: Class<T>) {
        initMyRetrofitGoValue(
            classes
        )
    }

    /**
     * 获取MyRetrofitGo注解loading和cache
     */
    fun <T> initMyRetrofitGoValue(cla: Class<T>) {
        var methods = cla.methods
        loop@ for (method in methods) {

            var postAnnotation = method.getAnnotation(POST::class.java)
            var getAnnotation = method.getAnnotation(GET::class.java)
            var path: String? = null
            when {
                postAnnotation != null -> path = postAnnotation.value
                getAnnotation != null -> path = getAnnotation.value
                else -> break@loop
            }


            var loading = method.getAnnotation(MyRetrofitGo::class.java).myLoading
            var cache = method.getAnnotation(MyRetrofitGo::class.java).myCache
            var hasCacheLoading = method.getAnnotation(MyRetrofitGo::class.java).myHasCacheLoading
            var tag = method.getAnnotation(MyRetrofitGo::class.java).myTag
            var failToast = method.getAnnotation(MyRetrofitGo::class.java).myFailToast
            var successCode = method.getAnnotation(MyRetrofitGo::class.java).mySuccessCode
            var failCode = method.getAnnotation(MyRetrofitGo::class.java).myFailCode

            value[path] = MyRetrofitGoValue(loading, cache, hasCacheLoading, tag,failToast,successCode,failCode)

        }
    }
}

