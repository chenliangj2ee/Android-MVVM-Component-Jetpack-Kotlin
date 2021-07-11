package com.chenliang.baselibrary.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Debug
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.R
import com.google.gson.Gson
import com.tbruyelle.rxpermissions3.RxPermissions
import gorden.rxbus2.RxBus
import kotlinx.android.synthetic.main.base_toast.view.*
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.regex.Pattern

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

var toastData = HashMap<String, String>()
fun Any.toast(msg: String) {

    if (toastData.containsKey(msg))
        return
    toastData[msg] = msg
    var view = View.inflate(BaseInit.con, R.layout.base_toast, null)
    var message = view.message
    message.text = msg
    var toast = Toast(BaseInit.con)
    toast.view = view
    toast!!.setGravity(Gravity.CENTER, 0, 0)
    toast!!.duration = Toast.LENGTH_SHORT
    toast.show()
    Handler().postDelayed(Runnable { toastData.remove(msg) }, 1500)

}

fun Any.logJson() {
    Log.i("MyLog", Gson().toJson(this))
    Log.i(this::class.java.simpleName, Gson().toJson(this))
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

/**
 * 手机号格式判断
 */
fun String.isPhone() = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(this).matches()

/**
 * 邮箱格式判断
 */
fun String.isEmail() =
    Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]\$").matcher(this).matches()


fun Drawable.bitmap(): Bitmap {
    val w: Int = this.intrinsicWidth
    val h: Int = this.intrinsicHeight
    val config =
        if (this.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
    val bitmap = Bitmap.createBitmap(w, h, config)
    val canvas = Canvas(bitmap)
    this.setBounds(0, 0, w, h)
    this.draw(canvas)
    return bitmap
}

fun Bitmap.drawable() = BitmapDrawable(this)

fun Bitmap.toZoomImage(w: Int, h: Int): Bitmap {
    val width: Int = this.width
    val height: Int = this.height
    val scaleWidth = w as Float / width
    val scaleHeight = h as Float / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun <T> Context.goto(cls: Class<T>, vararg values: Any) {

    var size = values.size - 1

    if (values.size % 2 != 0) {
        throw Exception("${this::class.simpleName} goto(path,values) values参数必须为偶数对...")
    }

    var intent = Intent(this, cls)

    for (index in 0..size step 2) {
        var key = values[index]
        var value = values[values.indexOf(key) + 1]
        when (value) {
            is Int -> intent.putExtra(key.toString(), value)
            is Long -> intent.putExtra(key.toString(), value)
            is String -> intent.putExtra(key.toString(), value)
            is Boolean -> intent.putExtra(key.toString(), value)
            is Double -> intent.putExtra(key.toString(), value)
            is Float -> intent.putExtra(key.toString(), value)
            is Serializable -> intent.putExtra(key.toString(), value)
        }
    }


    startActivity(intent)
    if (this is Activity) {
        overridePendingTransition(R.anim.base_right_in, R.anim.base_left_out)
    }
}

fun Any.goto(path: String, vararg values: Any) {

    var size = values.size - 1
    if (values.size % 2 != 0) {
        throw Exception("${this::class.simpleName} goto(path,values) values参数必须为偶数对...")
    }

    var post = ARouter.getInstance()
        .build(path).withTransition(R.anim.base_right_in, R.anim.base_left_out)

    for (index in 0..size step 2) {
        var key = values[index]
        var value = values[values.indexOf(key) + 1]
        when (value) {
            is Int -> post.withInt(key.toString(), value)
            is Long -> post.withLong(key.toString(), value)
            is String -> post.withString(key.toString(), value)
            is Boolean -> post.withBoolean(key.toString(), value)
            is Double -> post.withDouble(key.toString(), value)
            is Float -> post.withFloat(key.toString(), value)
            is Serializable -> post.withSerializable(key.toString(), value)
        }
    }
    if (this is Context) {
        post.navigation(this)
    } else {
        post.navigation()
    }

}

fun Any.send(code: Int) {
    RxBus.get().send(code)
}

fun Any.sendSelf(code: Int) {
    RxBus.get().send(code, this)
    RxBus.get().send(this)
}

fun Any.sendSelf() {
    RxBus.get().send(this)
}

/**
 * 耗时操作检测，默认100毫秒
 */
fun Any.anrCheck(func: () -> Unit) {
    anrCheck(100) { func() }
}

/**
 * 耗时操作检测，自定义时长
 */
fun Any.anrCheck(time: Int, func: () -> Unit) {
    var start = System.currentTimeMillis();
    func()
    var end = System.currentTimeMillis();
    if (end - start > time) {
        logE("--------------------------------------------------------------------------------------")
        logE("--                                                                                  --")
        logE("--                                                                                  --")
        logE("--                                                                                  --")
        logE("--                              耗时操作:${end - start}毫秒                            ")
        logE("--                                                                                  --")
        logE("--                                                                                  --")
        logE("--                                                                                  --")
        logE("--------------------------------------------------------------------------------------")
        throw Exception("${this::class.simpleName} initCreate耗时太长，请优化...")
    }
}