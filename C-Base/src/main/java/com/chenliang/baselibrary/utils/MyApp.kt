package com.chenliang.baselibrary.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Process
import com.chenliang.baselibrary.BaseInit
import kotlin.system.exitProcess

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.utils
 * @author: chenliang
 * @date: 2021/07/09
 */
object MyApp {

    /**
     * tag app：是否是主进程
     * 是否是主进程
     * @param context Context
     * @return Boolean
     */
    fun isMainProcess(): Boolean {
        val procName = getCurrentProcessName()
        return procName == null || procName.equals(BaseInit.con!!.packageName, ignoreCase = true)
    }

    /**
     * tag app：获取当前进程名字
     * 获取当前进程名字
     * @param context Context
     * @return String?
     */
    private fun getCurrentProcessName(): String? {
        val pid = Process.myPid()
        val mActivityManager = BaseInit.con!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in mActivityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }


    /**
     * tag app：系统分享
     * 系统分享
     * @param context Context
     * @param title String?
     * @param content String?
     * @param dialogTitle String?
     */
    fun share(context: Context, title: String?, content: String?, dialogTitle: String?) {
        val intentItem = Intent(Intent.ACTION_SEND)
        intentItem.type = "text/plain"
        intentItem.putExtra(Intent.EXTRA_SUBJECT, title)
        intentItem.putExtra(Intent.EXTRA_TEXT, content)
        intentItem.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(Intent.createChooser(intentItem, dialogTitle))
    }

    /**
     * tag app： 判断是否前台运行
     *  判断是否前台运行
     * @return Boolean
     */
    fun isAppRunningForeground(): Boolean {
        val activityManager = BaseInit.con!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcessInfos = activityManager.runningAppProcesses ?: return false
        val packageName = BaseInit.con!!.packageName
        for (appProcessInfo in runningAppProcessInfos) {
            if (appProcessInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                && appProcessInfo.processName == packageName
            ) {
                return true
            }
        }
        return false
    }

    /**
     * tag app：获取版本名称
     * 获取版本名称
     * @param context Context
     * @return String?
     */
    @Synchronized
    fun getVersionName(context: Context): String? {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                context.packageName, 0
            )
            return packageInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }


    /**
     * tag app：获取版本号
     * 获取版本号
     * @param context Context
     * @return Int
     */
    @Synchronized
    fun getVersionCode(context: Context): Int {
        try {
            val packageManager = context.packageManager
            val packageInfo = packageManager.getPackageInfo(
                context.packageName, 0
            )
            return packageInfo.versionCode
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * tag app：退出应用
     * 退出应用
     */
    fun exit() {
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }

    /**
     * tag app：打电话
     * 打电话
     * @param phone String
     * @return Intent?
     */
    fun callPhone(con: Context, phone: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        intent.data = Uri.parse("tel:$phone")
        con.startActivity(intent)
    }


    /**
     * tag app：发送短信
     * 发送短信
     * @param phoneNumber
     * @param message
     */
    fun sendMessage(con: Context, phone: String, message: String?) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phone"))
        intent.putExtra("sms_body", message)
        con.startActivity(intent)
    }


    /**
     * tag app： activity透明
     * activity透明
     * @param activity Activity
     */
    fun activityToTranslucent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            convertActivityToTranslucentAfterL(activity)
        } else {
            convertActivityToTranslucentBeforeL(activity)
        }
    }


    private fun convertActivityToTranslucentBeforeL(activity: Activity?) {
        try {
            val classes = Activity::class.java.declaredClasses
            var translucentConversionListenerClazz: Class<*>? = null
            for (clazz in classes) {
                if (clazz.simpleName.contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz
                }
            }
            val method = Activity::class.java.getDeclaredMethod(
                "convertToTranslucent",
                translucentConversionListenerClazz
            )
            method.isAccessible = true
            method.invoke(
                activity, *arrayOf<Any?>(
                    null
                )
            )
        } catch (t: Throwable) {
        }
    }

    private fun convertActivityToTranslucentAfterL(activity: Activity) {
        try {
            val getActivityOptions = Activity::class.java.getDeclaredMethod("getActivityOptions")
            getActivityOptions.isAccessible = true
            val options = getActivityOptions.invoke(activity)
            val classes = Activity::class.java.declaredClasses
            var translucentConversionListenerClazz: Class<*>? = null
            for (clazz in classes) {
                if (clazz.simpleName.contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz
                }
            }
            val convertToTranslucent = Activity::class.java.getDeclaredMethod(
                "convertToTranslucent",
                translucentConversionListenerClazz, ActivityOptions::class.java
            )
            convertToTranslucent.isAccessible = true
            convertToTranslucent.invoke(activity, null, options)
        } catch (t: Throwable) {
        }
    }

    fun openBrowser(url: String) {
        log("openBrowser：$url")
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
        BaseInit.con?.startActivity(intent);
    }
}