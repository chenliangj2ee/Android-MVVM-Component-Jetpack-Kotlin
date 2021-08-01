package com.chenliang.baselibrary.net

import java.io.Serializable

open class BaseResponse<T> : Serializable {
    var cache = false
    var code: Int = -1
    var message: String = "程序异常！"
    var data: T = Any() as T

    /**
     * 网络数据请求成功
     */
    fun y(func: (data: T) -> Unit) {
        if (this.code == 0 && !this.cache) func(data)
    }

    /**
     * 缓存数据请求成功
     */
    fun c(func: (data: T) -> Unit) {
        if (this.code == 0 && this.cache) func(data)
    }

    /**
     * 网络数据请求失败
     */
    fun n(func: (message: String) -> Unit) {
        if (this.code != 0 && !this.cache) func(message)
    }

}
