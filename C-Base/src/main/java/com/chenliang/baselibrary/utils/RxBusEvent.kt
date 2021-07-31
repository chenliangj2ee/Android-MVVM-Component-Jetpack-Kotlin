package com.chenliang.baselibrary.utils

import android.content.Intent
import java.io.Serializable

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.utils
 * @author: chenliang
 * @date: 2021/07/20
 */
class RxBusEvent<T>(o: T, back: ((intent: Intent) -> Unit)) : Serializable {
    var data: T? = o
    private var back: ((intent: Intent) -> Unit) = back
    fun callback(vararg values: Any) {
        var intent = Intent()
        intent.put(*values)
        back(intent)
    }
}