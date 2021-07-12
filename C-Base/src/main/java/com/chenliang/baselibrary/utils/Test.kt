package com.chenliang.baselibrary.utils

import com.chenliang.baselibrary.net.BaseResponse
import com.chenliang.baselibrary.net.log.BaseBeanLog

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.utils
 * @author: chenliang
 * @date: 2021/07/12
 */
class Test<T> : BaseResponse<T>() {
    var name = ""
    fun print() {
        name = this::class.java.genericSuperclass.toString()
        log("name--------------------$name")
    }

    init {

    }

}