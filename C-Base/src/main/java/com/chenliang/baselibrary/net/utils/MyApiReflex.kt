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


            var loading = method.getAnnotation(MyRetrofitGo::class.java).mLoading
            var cache = method.getAnnotation(MyRetrofitGo::class.java).mCache
            var hasCacheLoading = method.getAnnotation(MyRetrofitGo::class.java).mHasCacheLoading
            var tag = method.getAnnotation(MyRetrofitGo::class.java).mTag
            var failToast = method.getAnnotation(MyRetrofitGo::class.java).mFailToast
            var successCode = method.getAnnotation(MyRetrofitGo::class.java).mSuccessCode
            var failCode = method.getAnnotation(MyRetrofitGo::class.java).mFailCode

            value[path] = MyRetrofitGoValue(loading, cache, hasCacheLoading, tag,failToast,successCode,failCode)

        }
    }
}

