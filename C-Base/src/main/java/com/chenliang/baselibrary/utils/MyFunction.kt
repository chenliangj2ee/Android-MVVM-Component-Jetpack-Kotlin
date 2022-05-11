package com.chenliang.baselibrary.utils

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.ViewTarget
import com.chenliang.annotation.MyRouteUtils
import com.chenliang.baselibrary.BaseInit
import com.chenliang.baselibrary.R
import com.chenliang.baselibrary.base.MyBaseViewModel
import com.chenliang.baselibrary.base.MyDefaultFragment
import com.chenliang.baselibrary.bean.BeanUser
import com.chenliang.baselibrary.utils.*
import com.chenliang.baselibrary.view.MyImageView
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.tbruyelle.rxpermissions3.RxPermissions
import gorden.rxbus2.RxBus
import kotlinx.android.synthetic.main.base_exception_dialog.view.*
import kotlinx.android.synthetic.main.base_toast.view.*
import kotlinx.android.synthetic.main.base_toast.view.message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap


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
    this.visibility = if (show) View.VISIBLE else View.GONE
}

/**
 * 是否隐藏
 */
fun View.hide(hide: Boolean) = this.apply {
    this.visibility = if (hide) View.GONE else View.VISIBLE;
}

/**
 * 是否隐藏
 */
fun View.invisible(hide: Boolean) = this.apply {
    this.visibility = if (hide) View.INVISIBLE else View.VISIBLE;
}

private var toastData = HashMap<String, String>()

/**
 * 自定义toast
 */
fun Any.toast(msg: String) {

    if (msg.isNullOrEmpty())
        return

    if (toastData.containsKey(msg))
        return
    try {
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
    } catch (e: java.lang.Exception) {

    }


}


fun Any.log(message: String) {
    val className = Thread.currentThread().stackTrace[3].className
    val fileName = Thread.currentThread().stackTrace[3].fileName
    val methodName = Thread.currentThread().stackTrace[3].methodName
    val lineNumber = Thread.currentThread().stackTrace[3].lineNumber

    var message =
        "at $className.$methodName($fileName:$lineNumber)\n" +
                "|      日志：$message\n————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————"

    Log.i("MyLog", message)
}

fun Any.log(tag: String, message: String) {
    val className = Thread.currentThread().stackTrace[3].className
    val fileName = Thread.currentThread().stackTrace[3].fileName
    val methodName = Thread.currentThread().stackTrace[3].methodName
    val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
    var message2 =
        ":\n|\n|     at $className.$methodName($fileName:$lineNumber)\n" +
                "|      日志：$message\n|\n————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————"

    Log.i("MyLog", message2)
    Log.i(tag, message2)
}

fun Any.loge(message: String) {
    val className = Thread.currentThread().stackTrace[3].className
    val fileName = Thread.currentThread().stackTrace[3].fileName
    val methodName = Thread.currentThread().stackTrace[3].methodName
    val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
    var message =
        "at $className.$methodName($fileName:$lineNumber)\n" +
                "|      日志：$message\n————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————"

    Log.e("MyLog", message)
}

fun Any.loge(tag: String, message: String) {

    val className = Thread.currentThread().stackTrace[3].className
    val fileName = Thread.currentThread().stackTrace[3].fileName
    val methodName = Thread.currentThread().stackTrace[3].methodName
    val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
    var message =
        ":\n|\n|     at $className.$methodName($fileName:$lineNumber)\n" +
                "|      日志：$message\n|\n————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————"

    Log.e("MyLog", message)
    Log.e(tag, message)
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
fun View.click(func: (view: View) -> Unit) {
    this.isClickable = true
    this.setOnClickListener {
        func(it)
    }
}

inline fun View.goto(path: String, vararg values: Any) = this.apply {
    this.isClickable = true
    this.setOnClickListener {
        this.context?.goto(path, *values)
    }
}

inline fun <T> View.goto(cls: Class<T>, vararg values: Any) = this.apply {
    this.isClickable = true
    this.setOnClickListener {
        this.context?.goto(cls, *values)
    }
}

/**
 * 创建ViewModel
 * @receiver AppCompatActivity
 * @param modelClass Class<T>
 * @return T
 */
fun <T : ViewModel> AppCompatActivity.initVM(modelClass: Class<T>) =
    ViewModelProvider(this).get(modelClass)

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
    return MyNetWorkUtils.isConnected()
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
fun Array<String>.checkPermissions(act: AppCompatActivity, func: (boo: Boolean) -> Unit) {
    var array = this
    var per = RxPermissions(act)
    per.request(*array)
        .subscribe {
            func(it)
            if (it == false) {
                dialog("使用该功能，需要开启权限，鉴于您禁用相关权限，请手动设置开启权限").n { }
                    .y {
                        val packageURI = Uri.parse("package:" + BaseInit.con!!.packageName)
                        val intent =
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
                        BaseInit.con!!.startActivity(intent)
                    }
                    .show(BaseInit.act as AppCompatActivity)
            }
        }
}

fun Any.toSettings() {
    val packageURI = Uri.parse("package:" + BaseInit.con!!.packageName)
    val intent =
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
    BaseInit.con!!.startActivity(intent)
}

/**
 * dip转px
 * @return Int
 */
fun Int.dip2px(): Int {
    return MyScreen.dip2px(BaseInit.con!!, this.toFloat())
}

/**
 * dip转px
 * @return Int
 */
fun TextView.setTextSizeDip(size: Int) {
    this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size.toFloat())
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
    try {
        return SimpleDateFormat(pattern).format(this).toString()
    } catch (e: Exception) {
        log("时间格式化失败" + e.message)
        return ""
    }

}

/**
 * 时间戳字符串格式化:var time="13239100192"; time.date("yyyy-MM-dd")
 * @receiver String
 * @param pattern String
 * @return String
 */
fun String.dateParse(from: String, to: String) = SimpleDateFormat(from).parse(this).time.date(to)

/**
 * 时间戳字符串格式化:var time="13239100192"; time.date("yyyy-MM-dd")
 * @receiver String
 * @param pattern String
 * @return String
 */
fun String.dateToLong(from: String) = if (this == "") 0 else SimpleDateFormat(from).parse(this).time

/**
 * 时间戳long格式化:var time=13239100192; time.date("yyyy-MM-dd")
 * @receiver String
 * @param pattern String
 * @return String
 */
fun Long.date(pattern: String): String {
    return SimpleDateFormat(pattern).format(this)
}

/**2022-02-18T 01:00:00+08:00
 * 2022-01-27T 16:11:58.857+08:00
 */
fun String.dateT(pattern: String): String {
//    log("时间格式化：${this}")
    if (this == "") {
        return ""
    }
    return try {
        var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ")
        var timeZone = TimeZone.getTimeZone("GMT+8")
        dateFormat.timeZone = timeZone
        var date = dateFormat.parse(this)
        dateFormat.applyPattern(pattern)
        dateFormat.format(date)
    } catch (e: java.lang.Exception) {
        dateParse("yyyy-MM-dd'T'HH:mm:ss", pattern)
    }

}

fun String.format(pattern: String) = this.apply {
    return String.format(pattern, this)
}

/**
 * 手机号格式判断
 */
fun String.isMobilePhone() = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$").matcher(this).matches()

/**
 * 手机号格式判断
 */
fun String.isNumber() = Pattern.compile("^[0-9]*\$").matcher(this).matches()

/**
 * 邮箱格式判断
 */
fun String.isEmail() =
    Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]\$").matcher(this).matches()

/**
 * 长度为min-max的所有字符
 */
fun String.isLength(min: Int, max: Int) =
    Pattern.compile("^.{$min,$max}\$").matcher(this).matches()

/**
 * 英文和数字
 */
fun String.isAZ09All() =
    Pattern.compile("^[A-Za-z0-9]+\$").matcher(this).matches()

/**
 * 26个英文，包含大小写
 */
fun String.isAAaz() =
    Pattern.compile("^[A-Za-z]+\$").matcher(this).matches()

/**
 * 26个大写英文
 */
fun String.isAZ() =
    Pattern.compile("^[A-Z]+\$").matcher(this).matches()

/**
 * 26个小写英文
 */
fun String.isaz() =
    Pattern.compile("^[a-z]+\$").matcher(this).matches()

/**
 * 座机号码
 */
fun String.isPhone() =
    Pattern.compile("^(\\(\\d{3,4}-)|\\d{3.4}-)?\\d{7,8}\$").matcher(this).matches()

/**
 * 身份证
 */
fun String.isID() =
    Pattern.compile("^\\d{15}\$)|(^\\d{18}\$)|(^\\d{17}(\\d|X|x)\$").matcher(this).matches()

fun Drawable.toBitmap(): Bitmap {
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

fun Bitmap.toDrawable() = BitmapDrawable(this)

fun Bitmap.toZoomImage(w: Int, h: Int): Bitmap {
    val width: Int = this.width
    val height: Int = this.height
    val scaleWidth = w as Float / width
    val scaleHeight = h as Float / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun <T> Fragment.goto(cls: Class<T>, vararg values: Any): Fragment {
    return context!!.goto(cls, *values)
}

fun Fragment.goto(path: String, vararg values: Any): Fragment {
    return context!!.goto(path, *values)
}

fun Context.gotoFinish(path: String, vararg values: Any): Fragment {
    var result = BaseInit.con!!.goto(path, *values)
    if (this is Activity) {
        finish()
    }
    return result
}


fun <T> Context.gotoFinish(cls: Class<T>, vararg values: Any): Fragment {
    var result = BaseInit.con!!.goto(cls, *values)
    if (this is Activity) {
        finish()
    }
    return result
}

fun Fragment.gotoFinish(path: String, vararg values: Any): Fragment {
    var result = BaseInit.con!!.goto(path, *values)
    activity?.finish()
    return result
}

fun <T> Fragment.gotoFinish(cls: Class<T>, vararg values: Any): Fragment {
    var result = BaseInit.con!!.goto(cls, *values)
    activity?.finish()
    return result
}

fun <T> Context.goto(cls: Class<T>, vararg values: Any?): Fragment {

    var size = values.size - 1

    if (values.size % 2 != 0) {
        throw Exception("${this::class.simpleName} goto(path,values) values参数必须为偶数对...")
    }

    if (Activity::class.java.isAssignableFrom(cls)) {
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
        return MyDefaultFragment()
    } else {
        var fragment = cls.newInstance() as Fragment
        var bundle = Bundle()

        for (index in 0..size step 2) {
            var key = values[index]
            var value = values[values.indexOf(key) + 1]
            when (value) {
                is Int -> bundle.putInt(key.toString(), value)
                is Long -> bundle.putLong(key.toString(), value)
                is String -> bundle.putString(key.toString(), value)
                is Boolean -> bundle.putBoolean(key.toString(), value)
                is Double -> bundle.putDouble(key.toString(), value)
                is Float -> bundle.putFloat(key.toString(), value)
                is Serializable -> bundle.putSerializable(key.toString(), value)
            }
        }

        fragment.arguments = bundle
        return fragment
    }


}

fun <T> Activity.gotoBottom(cls: Class<T>, vararg values: Any?) {
    var size = values.size - 1

    if (values.size % 2 != 0) {
        throw Exception("${this::class.simpleName} goto(path,values) values参数必须为偶数对...")
    }

    var intent = Intent(BaseInit.con!!, cls)

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
    intent.putExtra("bottom", true)
    startActivity(intent)
    overridePendingTransition(R.anim.base_bottom_in, R.anim.base_top_out)


}

fun Any.postDelayed(delay: Long, func: () -> Unit) {
    var handler: Handler? = Handler()
    var run = Runnable {
        func()
        handler = null
    }
    handler?.postDelayed(run, delay)
}

fun Context.goto(path: String, vararg values: Any): Fragment {

//    log("goto path: $path -----------------------------")

    var size = values.size - 1

    if (values.size % 2 != 0) {
        throw Exception("${this::class.simpleName} goto(path,values) values参数必须为偶数对...")
    }

    var key = ""
    var classPath = ""
    if (path.contains("|")) {
        key = path.split("|")[0]
        classPath = path.split("|")[1]
    } else {
        key = path;
//        log("goto MyRouteUtils.path[key]: ${MyRouteUtils.path[key]} -----------------------------")
        if (MyRouteUtils.path[key] == null) {
//            toast("找不到路由对应的页面：$key")
            loge("找不到路由对应的页面：$key")
            return MyDefaultFragment()
        }
        classPath = MyRouteUtils.path[key]!!.split("|")[1]
    }
//    log("goto classPath: $classPath -----------------------------")
    var cls = Class.forName(classPath)
    if (Activity::class.java.isAssignableFrom(cls)) {
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
        return MyDefaultFragment()
    } else {
        var fragment = cls.newInstance() as Fragment
        var bundle = Bundle()

        for (index in 0..size step 2) {
            var key = values[index]
            var value = values[values.indexOf(key) + 1]
            when (value) {
                is Int -> bundle.putInt(key.toString(), value)
                is Long -> bundle.putLong(key.toString(), value)
                is String -> bundle.putString(key.toString(), value)
                is Boolean -> bundle.putBoolean(key.toString(), value)
                is Double -> bundle.putDouble(key.toString(), value)
                is Float -> bundle.putFloat(key.toString(), value)
                is Serializable -> bundle.putSerializable(key.toString(), value)
            }
        }

        fragment.arguments = bundle
        return fragment
    }
}

/**
 * 阿里路由调用配置
 */
//fun Any.goto(path: String, vararg values: Any): Any? {
//
//    var size = values.size - 1
//    if (values.size % 2 != 0) {
//        throw Exception("${this::class.simpleName} goto(path,values) values参数必须为偶数对...")
//    }
//
//    var post = ARouter.getInstance()
//        .build(path).withTransition(R.anim.base_right_in, R.anim.base_left_out)
//
//    for (index in 0..size step 2) {
//        var key = values[index]
//        var value = values[values.indexOf(key) + 1]
//        when (value) {
//            is Int -> post.withInt(key.toString(), value)
//            is Long -> post.withLong(key.toString(), value)
//            is String -> post.withString(key.toString(), value)
//            is Boolean -> post.withBoolean(key.toString(), value)
//            is Double -> post.withDouble(key.toString(), value)
//            is Float -> post.withFloat(key.toString(), value)
//            is Serializable -> post.withSerializable(key.toString(), value)
//        }
//    }
//    if (this is Context) {
//        var result = post.navigation(this)
//        if (result == null) {
//            result = MyDefaultFragment()
//        }
//        return result
//    } else {
//        var result = post.navigation()
//        if (result == null) {
//            result = MyDefaultFragment()
//        }
//        return result
//    }
//
//
//}

fun Any.send(code: Int) {
    RxBus.get().send(code)
}

fun Any.send(code: Int, f: ((intent: Intent) -> Unit)) {
    RxBus.get().send(code, RxBusEvent(Any(), f))
}

fun Any.sendSelf(code: Int) {

    RxBus.get().send(code, this)
}

fun Any.sendSelf(code: Int, f: ((intent: Intent) -> Unit)) {
    RxBus.get().send(code, RxBusEvent(this, f))
}


fun Intent.put(vararg values: Any): Intent {
    if (values.size % 2 != 0) {
        throw Exception("${this::class.simpleName} Intent.put values参数必须为偶数对...")
    }
    var size = values.size - 1
    for (index in 0..size step 2) {
        var key = values[index]
        var value = values[values.indexOf(key) + 1]
        when (value) {
            is Int -> this.putExtra(key.toString(), value)
            is Long -> this.putExtra(key.toString(), value)
            is String -> this.putExtra(key.toString(), value)
            is Boolean -> this.putExtra(key.toString(), value)
            is Double -> this.putExtra(key.toString(), value)
            is Float -> this.putExtra(key.toString(), value)
            is Serializable -> this.putExtra(key.toString(), value)
        }
    }
    return this
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
        loge("${this::class.simpleName}  耗时太长:${end - start}毫秒，请优化...")
    } else {
//        log("操作时长:${end - start}毫秒 ")
    }
}

fun Any.dialog(message: String): MyDialog {
    return MyDialog().message(message)
}

fun Any.dialog(): MyDialog {
    return MyDialog().message("")
}

fun String.check(vararg checks: Any): Boolean {

    var text = this.trim().replace(" ", "")

    var size = checks.size - 1

    for (index in 0..size step 2) {

        var check = checks[index]
        var message = checks[checks.indexOf(check) + 1] as String


        when (check) {
            is Int -> {
                when (check) {
                    MyCheck.empty -> {
                        if (text.isNullOrEmpty()) {
                            toast(message)
                            return true
                        }
                    }
                    MyCheck.mobilePhone -> {
                        if (!text.isMobilePhone()) {
                            toast(message)
                            return true
                        }
                    }
                    MyCheck.phone -> {
                        if (!text.isPhone()) {
                            toast(message)
                            return true
                        }
                    }
                    MyCheck.AZ -> {
                        if (!text.isAZ()) {
                            toast(message)
                            return true
                        }
                    }
                    MyCheck.az -> {
                        if (!text.isaz()) {
                            toast(message)
                            return true
                        }
                    }
                    MyCheck.AZaz -> {
                        if (!text.isAAaz()) {
                            toast(message)
                            return true
                        }
                    }
                    MyCheck.AZ09All -> {
                        if (!text.isAZ09All()) {
                            toast(message)
                            return true
                        }
                    }
                    MyCheck.number -> {
                        if (!text.isNumber()) {
                            toast(message)
                            return true
                        }
                    }
                    MyCheck.id -> {
                        if (!text.isNumber()) {
                            toast(message)
                            return true
                        }
                    }

                }
            }
            is ChenkLength -> {
                if (text.length < check.min || text.length > check.max) {
                    toast(message)
                    return true
                }
            }

        }
    }

    return false

}

fun String.insert(tag: String, vararg positions: Int): String {

    var sb = StringBuffer(this.trim().replace(" ", "").replace(tag, ""))

    var offset = 0
    positions.forEach {
        if (it + offset < sb.length)
            sb.insert(it + offset, tag)
        offset++
    }
    return sb.toString()
}


fun EditText.changed(func: (str: String) -> Unit) {

    var listener = object : TextWatcher {
        var boo = false
        override fun afterTextChanged(s: Editable?) {
            func(text.toString())
            if (!this.boo) {
                setSelection(text.toString().length)
                boo = true
            }
            this
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }

    this.addTextChangedListener(listener)
}

fun Any.editChanged(vararg edits: EditText, func: () -> Unit) {
    for (edit in edits) {
        edit.changed { func() }
    }
}

/**
 * tag network：网络状态变化监听
 * @receiver Any
 * @param func Function1<[@kotlin.ParameterName] Boolean, Unit>
 */
fun Any.networkChange(func: (enable: Boolean) -> Unit) {

    class CallBack : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            GlobalScope.launch(Dispatchers.Main) {
                func(true)
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            GlobalScope.launch(Dispatchers.Main) {
                func(false)
            }
        }
    }

    val connMgr =
        BaseInit.con?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connMgr?.registerNetworkCallback(NetworkRequest.Builder().build(), CallBack())
}

/**
 * 将图片下载到本地
 * @receiver ImageView
 * @param url String
 */
fun ImageView.download(url: String) {

    var downloadListener = object : RequestListener<File> {


        override fun onResourceReady(
            resource: File?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<File>?,
            dataSource: com.bumptech.glide.load.DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            saveFileFromUri(context!!, Uri.fromFile(resource))
            return true
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<File>?,
            isFirstResource: Boolean
        ): Boolean {
            toast("图片下载视频")
            return false;
        }
    }

    Glide.with(context!!)
        .downloadOnly()
        .load(url)
        .listener(downloadListener)
        .preload();
}

/**
 * 将图片下载到本地
 * @receiver ImageView
 * @param url String
 */
fun ImageView.load(url: String) {
    var bg = background
    if (bg != null) {
        Glide.with(context!!)
            .load(url).into(this)
    } else {
        var options = RequestOptions().error(R.drawable.load_default)
        Glide.with(context!!)
            .load(url).apply(options).into(this)
    }


}

/**
 * 将图片下载到本地
 * @receiver ImageView
 * @param url String
 */
fun ImageView.loadGS(url: String) {
    var bg = background
    scaleType = ImageView.ScaleType.FIT_XY
    if (bg != null) {
        Glide.with(context!!)
            .load(url).into(this)
    } else {
        var options = RequestOptions().error(R.drawable.load_default)
//        Glide.with(context!!)
//            .load(url).dontAnimate()
//            .transform(BlurTransformation(context, 13)).apply(options).into(this)

        Glide.with(this)
            .load(url)
            .apply(bitmapTransform(GlideBlurTransformation(context)))
            .into(object : ViewTarget<ImageView?, Drawable?>(this) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?
                ) {
                    val current = resource.current
                    this@loadGS.setImageDrawable(current)
                }
            })

    }


}


/**
 * 将图片下载到本地
 * @receiver ImageView
 * @param url String
 */
fun ImageView.load(url: String? = "", radius: Int) {
//        log("加载图片 url：$url  radius:$radius ")
    if (this is MyImageView) {
        if (default != -1) {
            var options = RequestOptions()
                .transform(CenterCrop(), RoundedCorners((radius).dip2px()))
            Glide.with(context!!)
                .load(url)
                .error(default)
                .placeholder(default)
                .apply(options)
                .into(this)
            return
        } else {
            var options = RequestOptions()
                .transform(CenterCrop(), RoundedCorners((radius).dip2px()))
            Glide.with(context!!)
                .load(url)
                .apply(options)
                .into(this)
            return
        }

    }

    var options = RequestOptions()
        .transform(CenterCrop(), RoundedCorners((radius).dip2px()))
    Glide.with(context!!)
        .load(url)
        .apply(options)
        .error(R.drawable.load_default)
        .placeholder(R.drawable.load_default)
        .into(this)
}


/**
 * 将图片下载到本地
 * @receiver ImageView
 * @param url String
 */
fun ImageView.load(resourceId: Int, radius: Int) {
//    log("加载图片 resourceId：$resourceId  radius:$radius ")
    if (this is MyImageView) {
        if (default != -1) {
            var options = RequestOptions()
                .transform(CenterCrop(), RoundedCorners((radius).dip2px()))
            Glide.with(context!!)
                .load(resourceId)
                .error(default)
                .placeholder(default)
                .apply(options)
                .into(this)
            return
        } else {
            var options = RequestOptions()
                .transform(CenterCrop(), RoundedCorners((radius).dip2px()))
            Glide.with(context!!)
                .load(resourceId)
                .apply(options)
                .into(this)
            return
        }

    }

    var options = RequestOptions()
        .transform(CenterCrop(), RoundedCorners(radius))
    Glide.with(context!!)
        .load(resourceId)
        .apply(options)
//        .error(R.drawable.load_default)
//        .placeholder(R.drawable.load_default)
        .into(this)
}

private fun saveFileFromUri(context: Context, uri: Uri) {
    //系统相册目录
    val storePath = (Environment.getExternalStorageDirectory()
        .toString() + File.separator + Environment.DIRECTORY_DCIM
            + File.separator + "Camera" + File.separator + "new_" + File(uri.path + ".png").name)
    var inputStream: InputStream? = null
    var bos: BufferedOutputStream? = null
    try {
        inputStream = context.contentResolver.openInputStream(uri)
        bos = BufferedOutputStream(FileOutputStream(storePath, false))
        val buf = ByteArray(1024)
        inputStream!!.read(buf)
        do {
            bos.write(buf)
        } while (inputStream.read(buf) != -1)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        //发送广播通知系统图库刷新数据
        val uri2 = Uri.fromFile(File(storePath))
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri2))
        Any().toast("图片已保存到相册")
        try {
            inputStream?.close()
            bos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

fun EditText.focusableEnd() {
    setSelection(text.toString().length)
}

fun TabLayout.selected(func: (tab: TabLayout.Tab?) -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            func(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            func(tab)
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    })
}

@BindingAdapter(value = ["bindLoad", "bindRadius"], requireAll = false)
fun imageUrl(view: ImageView, bindLoad: String?, bindRadius: Int) {
    if (!bindLoad.isNullOrEmpty()) {
        if (bindRadius > 0) {
            view.load(bindLoad, bindRadius)

        } else {
            if (view is MyImageView && view.radius > 0)
                view.load(bindLoad, view.radius)
            else
                view.load(bindLoad)
        }

    } else {
        if (view is MyImageView) {
            if (view.radius > 0) {
                view.load(view.default, view.radius)
            } else if (bindRadius > 0) {
                view.load(view.default, bindRadius)
            }

        }

    }


}

@BindingAdapter(value = ["bindResourceId"])
fun bindSrc(view: ImageView, resource: Int) {
    view.setImageResource(resource);
}

@BindingAdapter(value = ["bindTint"])
fun bindTint(view: ImageView, color: String) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !color.isNullOrEmpty()) {
            view.imageTintList = ColorStateList.valueOf(Color.parseColor(color))
        }
    } catch (e: Exception) {
        e.loge("颜色值必须为：#FFFFFF格式")
    }
}

fun ImageView.tint(color: Int) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageTintList = ColorStateList.valueOf(color)
        }
    } catch (e: Exception) {
        e.loge("颜色值必须为：#FFFFFF格式")
    }
}

@BindingAdapter(value = ["bindResourceId"])
fun bindBackground(view: Button, resource: Int) {
    view.setBackgroundResource(resource);

}

@BindingAdapter(value = ["bindResourceId"])
fun bindTextViewBackground(view: TextView, resource: Int) {
    view.setBackgroundResource(resource);

}

@BindingAdapter(value = ["myTextColor"])
fun bindTextColor(view: TextView, color: String) {
    view.setTextColor((Color.parseColor(color)))

}

@BindingAdapter(value = ["myButtonColor"])
fun bindButtonTextColor(view: Button, color: String) {
    view.setTextColor((Color.parseColor(color)))

}

fun TextView.setHtmlText(text: String) {
    setText(Html.fromHtml(text))
}

fun MyBaseViewModel.body(vararg values: Any): HashMap<String, Any> {

    var size = values.size - 1
    if (values.size % 2 != 0) {
        throw Exception("${this::class.simpleName} body values参数必须为偶数对...")
    }
    var map = HashMap<String, Any>()
    for (index in 0..size step 2) {
        var key = values[index]
        var value = values[values.indexOf(key) + 1]
        map[key as String] = value
    }
    return map

}

fun RadioButton.checked(func: (isChecked: Boolean) -> Unit) {
    this.click {
        func(this.isChecked)
    }
}

fun CheckBox.checked(func: (isChecked: Boolean) -> Unit) {
    this.click {
        func(this.isChecked)
    }
}


fun Any.hideSoftInput(view: View) {
    val manager: InputMethodManager =
        BaseInit.con!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (manager != null) manager.hideSoftInputFromWindow(
        view.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )

}


fun <T> Any.fromJson(json: String): T {
    return Gson().fromJson(json, this::class.java) as T
}

fun String.timestamp(): Long {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date: Date = sdf.parse(this)
        date.time
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
    return 0
}


fun Long.handleDate(): String {
    var sdf = SimpleDateFormat("yyyy-MM-dd");
    var date = Date(this);
    var old = sdf.parse(sdf.format(date));
    var now = sdf.parse(sdf.format(Date()));
    var oldTime = old.time;
    var nowTime = now.time;

    var day = (nowTime - oldTime) / (24 * 60 * 60 * 1000);

    return when {
        day < 1 -> {
            "今天";
        }
        day == 1L -> {
            "昨天 "
        }
        else -> {
            var format = SimpleDateFormat("yyyy.MM.dd");
            format.format(date);
        }
    }
}

fun TextView.setBold(bold: Boolean) {
    typeface = if (bold) {
        Typeface.defaultFromStyle(Typeface.BOLD);
    } else {
        Typeface.defaultFromStyle(Typeface.NORMAL);
    }
}

fun Context.screenLandscape() {
    (this as Activity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
}

fun Context.screenPortrait() {
    (this as Activity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
}

fun Context.isNotificationEnabled(): Boolean {
    return NotificationManagerCompat.from(this).areNotificationsEnabled();
}

fun Context.gotoSetting() {
    var intent = Intent()
    when {
        Build.VERSION.SDK_INT >= 26 -> {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS";
            intent.putExtra("android.provider.extra.APP_PACKAGE", packageName);
        }
        Build.VERSION.SDK_INT >= 21 -> {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS";
            intent.putExtra("app_package", packageName);
            intent.putExtra("app_uid", applicationInfo.uid);
        }
        else -> {
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS";
            intent.data = Uri.fromParts("package", packageName, null);
        }
    }
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
    startActivity(intent);
}

fun Int.toTime(): String { //2*60*60*60
    var h = this / (60 * 60)
    var m = (this - this / (60 * 60)) / 60
    var s = (this - this / (60 * 60)) % 60


    if (h > 0) {

        var hs = if (h < 10) "0$h" else "$h"
        var ms = if (m < 10) "0$m" else "$m"
        var ss = if (s < 10) "0$s" else "$s"

        return "$hs:$ms:$ss"
    } else {
        var ms = if (m < 10) "0$m" else "$m"
        var ss = if (s < 10) "0$s" else "$s"
        return "$ms:$ss"
    }


}

fun Any.vibrate() {
    var vibrator = BaseInit.con?.getSystemService(VIBRATOR_SERVICE) as Vibrator
    if (vibrator.hasVibrator()) {
        var array = arrayListOf<Long>(0, 200, 100, 200)
        vibrator.vibrate(array.toLongArray(), -1)
    }
}

fun Context.playLocalVideo(url: String) {
    var intent = Intent(Intent.ACTION_VIEW)
    var file = File(url)
    var uri = Uri.fromFile(file);
    intent.setDataAndType(uri, "video/*");
    startActivity(intent);
}

fun Any.loading(vararg messages: String): Dialog {
    var dialog = BaseInit.act?.let { Dialog(it) }
    dialog?.setCancelable(false)
    dialog?.setContentView(R.layout.base_loading)
    if (!messages.isNullOrEmpty()) {
        dialog?.findViewById<TextView>(R.id.message)?.text = messages[0]
    }

    dialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent);
    dialog?.show()
    return dialog
}


@SuppressLint("WrongConstant")
fun Any.messageNotification(id: String, title: String, message: String) {

    log("通知》。。。。。。。。。。。。。")
}


fun Any.getBeanUser(): BeanUser? = BeanUser().get<BeanUser>()

fun String.anonymous(): String {
    if (this.isNullOrEmpty())
        return "匿名"
    return this.substring(0, 1) + "*".repeat(this.length - 1)
}

fun WebView.loadJs(name: String, vararg values: String) {

    var valueStr = ""
    for (value in values) {
        valueStr += ",'${value}'"
    }
    valueStr = valueStr.substring(1)
//    user?.token + "','" + webview.testId

    var bridge = "javascript:$name($valueStr)"

    log("loadjs:$bridge")

    loadUrl(bridge)
}

fun Activity.UI(func: () -> Unit) {
    runOnUiThread { func() }
}



/**
 * 保存view到图库
 */
fun View.saveToBitmap(path: String): Boolean {
    this.isDrawingCacheEnabled = true
    this.buildDrawingCache(true)
    var bitmap = this.getDrawingCache(true)
    val result: Bitmap
    val bmpSrcWidth: Int = bitmap.width
    val bmpSrcHeight: Int = bitmap.height

    val bmpDest =
        Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight, Bitmap.Config.ARGB_8888)
    if (null != bmpDest) {
        val canvas = Canvas(bmpDest)
        val rect = Rect(0, 0, bmpSrcWidth, bmpSrcHeight)
        canvas.drawBitmap(bitmap, rect, rect, null)
    } else {
        log("保存失败")
        return false
    }

    result = bmpDest

    if (bitmap != null && !bitmap.isRecycled) {
        bitmap.recycle()
        bitmap = null
    } else {
        log("保存失败")
        return false
    }
    this.isDrawingCacheEnabled = false

    if (result == null) {
        log("保存失败")
        return false
    }
    MediaStore.Images.Media.insertImage(
        context.contentResolver!!,
        result!!,
        getRandomPath("png"),
        "心情"
    ); // 名
    log("保存成功")
    return true
}

/**
 * 随机获取文件路径
 */
fun Any.getRandomPath(suffix: String) =
    Environment.getExternalStorageDirectory().path + "/" + System.currentTimeMillis() + (Math.random() * 10000F).toInt() + ".$suffix"

/**
 * 按位与运算-&：判断是否存在某个状态；
 */
fun Int.has(status: Int): Boolean {
//    log("has $status:"+(this and status) )
    return this and status > 0
}

fun Long.timeNumString(): String {
    return if (this >= 10) this.toString() else ("0$this")
}