package com.chenliang.baselibrary

import com.chenliang.baselibrary.annotation.MySpConfig
import com.chenliang.baselibrary.utils.MyDialog

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.utils
 * @author: chenliang
 * @date: 2021/07/29
 */


class MyConfig {
    @MySpConfig
    var isLogin = false;

    @MySpConfig
    var token = ""

    @MySpConfig
    var age = 0

    @MySpConfig
    var size = 0L

    @MySpConfig
    var mfloat = 0.0f

    @MySpConfig
    var mDouble = 0.0.toDouble()

    @MySpConfig
    var isFirst = false

    @MySpConfig
    var myObject: MyDialog? = null

    @MySpConfig
    var pushToken: String = ""

    @MySpConfig
    var isAgree: Boolean = false

    @MySpConfig
    var floatDialog: Boolean = false


    @MySpConfig
    var notifyDialog: Boolean = false

}