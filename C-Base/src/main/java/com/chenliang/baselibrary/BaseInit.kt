package com.chenliang.baselibrary

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chenliang.annotation.ApiModel
import com.chenliang.annotation.MyRouteUtils
import com.chenliang.baselibrary.bean.BeanUser
import com.chenliang.baselibrary.utils.*
import kotlin.concurrent.thread

@SuppressLint("StaticFieldLeak")
object BaseInit {
    /**
     * 主要用户startActivity调用
     */
    var con: Context? = null

    /**
     * 当前顶部activity，主要用户显示dialog使用
     */
    var act: AppCompatActivity? = null


    /**
     * 主要在SplashActivity启动时使用
     * 是否是冷启动，冷启动时，启动页时长设置为300，否则设置为1000
     */
    var icColdStart = true//是否是冷启动，默认是


    fun init(context: Application) {
        if (con == null) {
            con = context
        }

    }

    /**
     * 所有ApiService都需要在Application中调用找个方法来初始化
     */
    fun registerApi(vararg classes: Class<*>) {
        thread {
            for (item in classes) {
                MyRetrofitGoUtils.register(item)
            }
        }

    }


    fun openDev() {
        log("开发环境............................")
        ApiModel.openDev()
    }

    fun openTest() {
        log("测试环境............................")
        ApiModel.openTest()
    }

    fun openRelease() {
        log("生产环境............................")
        ApiModel.openRelease()
    }

    /**
     * 需要在Application初始化路由MyRoutePath,没个使用MyRoute注解的Module都会自动生成这个类
     */
    fun initMyRoute(any: Any) {
        var fields = any::class.java.declaredFields
        fields.forEach {
            it.isAccessible = true
            var name = it.name
            var value = it.get(any)
            if (value is String) {
                var key = value.split("|")[0]
                MyRouteUtils.path[key] = value
                Log.i("MyRouteUtils", "$key  :  $value")
            }

        }
    }

    /**
     * 退出登录》》获取到登录用户信息，清空缓存，将用户信息登录状态修改为false后，保存用户信息到sp，保证下次登录，账号可以正常的回显
     */
    fun exit() {
        log("调用BaseInit.exit退出")
        send(BusCode.EXIT)
        var user = getBeanUser()
        MySpUtis.clearAll()
        MyHttpDB.clearAll()
        var exitUser = BeanUser()
        exitUser.save()
        act?.goto("base/login")

    }

}