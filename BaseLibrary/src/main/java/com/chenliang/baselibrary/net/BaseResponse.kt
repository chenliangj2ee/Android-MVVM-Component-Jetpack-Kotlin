package com.chenliang.baselibrary.net

import java.io.Serializable

class BaseResponse<T> : Serializable {
    var cache = false
    var code: Int = -1
    var message: String = "程序异常！"
    var data: T? = null

}
