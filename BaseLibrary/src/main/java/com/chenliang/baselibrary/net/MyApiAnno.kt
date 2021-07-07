package com.chenliang.baselibrary.net

import com.xl.base.anno.MyRetrofitGo
import com.xl.base.anno.MyRetrofitGoValue
import retrofit2.http.GET
import retrofit2.http.POST

object MyApiAnno {

    var value = HashMap<String, MyRetrofitGoValue>()


    fun <T> register(classes: Class<T>) {
        initMyRetrofitGoValue(classes)
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


            var loading = method.getAnnotation(MyRetrofitGo::class.java).loading
            var cache = method.getAnnotation(MyRetrofitGo::class.java).cache
            var hasCacheLoading = method.getAnnotation(MyRetrofitGo::class.java).hasCacheLoading
            var tag = method.getAnnotation(MyRetrofitGo::class.java).tag

            value[path] = MyRetrofitGoValue(loading, cache, hasCacheLoading, tag)

        }
    }
}

