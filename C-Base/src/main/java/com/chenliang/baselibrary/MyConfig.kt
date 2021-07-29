package com.chenliang.baselibrary

import com.chenliang.baselibrary.annotation.MySpAnno
import com.chenliang.baselibrary.utils.MyDialog

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.utils
 * @author: chenliang
 * @date: 2021/07/29
 */


class MyConfig {
    @MySpAnno
    var isLogin = false;

    @MySpAnno
    var token = ""

    @MySpAnno
    var age = 0

    @MySpAnno
    var size = 0L

    @MySpAnno
    var mfloat = 0.0f

    @MySpAnno
    var mDouble = 0.0.toDouble()

    @MySpAnno
    var isFirst = false

    @MySpAnno
    var myObject: MyDialog? = null
}