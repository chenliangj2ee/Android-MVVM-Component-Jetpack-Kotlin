package com.chenliang.baselibrary.net

import java.io.Serializable

class BaseResponse<T> : Serializable {
    var cache = false
    var code: Int = -1
    var message: String = "程序异常！"
    var data: T? = null

    /**
     * 网络数据请求成功
     */
    fun y(func: () -> Unit) {
        if (this.code == 0 && !this.cache) func()
    }

    /**
     * 缓存数据请求成功
     */
    fun c(func: () -> Unit) {
        if (this.code == 0 && this.cache) func()
    }

    /**
     * 网络数据请求失败
     */
    fun n(func: () -> Unit) {
        if (this.code != 0 && !this.cache) func()
    }

}
