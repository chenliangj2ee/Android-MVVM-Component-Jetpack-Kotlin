package com.chenliang.baselibrary.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.R
import com.google.gson.Gson
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.base_toast.view.*
import java.text.SimpleDateFormat

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.utils
 * @author: chenliang
 * @date: 2021/07/09
 */

/**
 * 显示、隐藏view
 * @receiver View
 * @param show Boolean
 * @return View
 */
fun View.show(show: Boolean) = this.apply {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun Any.toast(msg: String) {
    var view = View.inflate(BaseInit.con, R.layout.base_toast, null)
    var message = view.message
    message.text = msg
    var toast = Toast(BaseInit.con)
    toast.view = view
    toast!!.setGravity(Gravity.CENTER, 0, 0)
    toast!!.duration = Toast.LENGTH_SHORT
    toast.show()

}

fun Any.log(message: String) {
    Log.i("MyLog", message)
    Log.i(this::class.java.simpleName, message)
}

fun Any.log(tag: String, message: String) {
    Log.i(tag, message)
    log(message)
}

fun Any.logE(message: String) {
    Log.e("MyLog", message)
    Log.e(this::class.java.simpleName, message)
}

fun Any.logE(tag: String, message: String) {
    Log.e(tag, message)
    logE(message)
}

/**
 * toJson
 * @receiver Any
 * @return String
 */
fun Any.toJson(): String = Gson().toJson(this)

/**
 * 点击事件错写
 * @receiver View
 * @param func Function1<[@kotlin.ParameterName] View, Unit>
 * @return View
 */
fun View.click(func: (view: View) -> Unit) = this.apply {
    this.setOnClickListener { func(it) }
}

/**
 * 创建ViewModel
 * @receiver AppCompatActivity
 * @param modelClass Class<T>
 * @return T
 */
fun <T : ViewModel> AppCompatActivity.initVM(modelClass: Class<T>) =
    ViewModelProvider(this)[modelClass]

/**
 * 打开无参Activity
 * @receiver Activity
 * @param cls Class<T>
 */
fun <T> Activity.toAct(cls: Class<T>) {
    startActivity(Intent(this, cls))
}

fun Any.hasNull(vararg args: String): Boolean {
    val array = arrayOf(args)
    var size = args.size - 1

    for (index in 0..size step 2) {
        var item = args[index]
        var message = args[index + 1]

        if (item == null) {
            toast(message)
            return true
        }

        when (item) {
            is String -> {
                if (item.isEmpty()) {
                    toast(message)
                    return true
                }
            }
        }
    }
    return false
}

/**
 * 创建ViewModel
 * @receiver Fragment
 * @param modelClass Class<T>
 * @return T
 */
fun <T : ViewModel> Fragment.initVM(modelClass: Class<T>) = ViewModelProvider(this)[modelClass]

/**
 * 判断是的有网络
 * @receiver Any
 * @return Boolean
 */
fun Any.hasNetWork(): Boolean {
    return MyNetWorkUtils.isConnected(BaseInit.con!!)
}


/**
 * 权限
 * @receiver Array<String>
 * @param act AppCompatActivity
 * @param func Function1<[@kotlin.ParameterName] Boolean, Unit>
 */
/*      demo
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
        ).check(this) {
            if (it) {
                toast("成功")
            } else {
                toast("失败")
            }
        }
 */
fun Array<String>.check(act: AppCompatActivity, func: (boo: Boolean) -> Unit) {
    var array = this
    var per = RxPermissions(act)
    per.request(*array)
        .subscribe {
            func(it)
        }
}

/**
 * dip转px
 * @return Int
 */
fun Int.dip2px(): Int {
    return MyScreen.dip2px(BaseInit.con!!, this.toFloat())
}

/**
 * px转dip
 * @return Int
 */
fun Int.px2dip(): Int {
    return MyScreen.px2dip(BaseInit.con!!, this)
}


/**
 * sp转px
 * @return Int
 */
fun Int.sp2px(): Int {
    return MyScreen.sp2px(BaseInit.con!!, this.toFloat())
}

/**
 * 时间戳字符串格式化:var time="13239100192"; time.date("yyyy-MM-dd")
 * @receiver String
 * @param pattern String
 * @return String
 */
fun String.date(pattern: String) = this.apply {
    var length = System.currentTimeMillis().toString().length;
    var time = this + "0".repeat(length - this.length)
    return SimpleDateFormat(pattern).format(time).toString()
}


/**
 * 时间戳long格式化:var time=13239100192; time.date("yyyy-MM-dd")
 * @receiver String
 * @param pattern String
 * @return String
 */
fun Long.date(pattern: String): String {
    return SimpleDateFormat(pattern).format(this)
}

fun String.format(pattern: String) = this.apply {
    return String.format(pattern, this)
}
