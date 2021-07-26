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
class Callback<T>(o: T, f: ((intent: Intent) -> Unit)) : Serializable {
    var obj: T? = o
    var func: ((intent: Intent) -> Unit)? = f
}