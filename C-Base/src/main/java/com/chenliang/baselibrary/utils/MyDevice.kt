package com.chenliang.baselibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import java.util.*

/**
 *
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.utils
 * @author: chenliang
 * @date: 2021/07/09
 */
object MyDevice {

    /**
     * tag device：获取设备SIM
     * 获取Sim
     * @param context Context
     * @return String
     */
    @SuppressLint("MissingPermission")
    fun getSim(context: Context): String {
        return try {
            if (getTelephonyManager(context).simSerialNumber == null) "" else getTelephonyManager(
                context
            ).simSerialNumber
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * tag device：获取设备号
     * 获取设备号
     * @param context Context
     * @return String
     */
    @SuppressLint("MissingPermission", "NewApi")
    fun getDeviceId(context: Context): String {
        return try {
            if (getTelephonyManager(context).imei == null) "" else getTelephonyManager(context).imei
        } catch (e: Exception) {
            ""
        }
    }

    private fun getTelephonyManager(context: Context): TelephonyManager {
        return context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    /**
     * tag device：获取UUID
     * 获取UUID
     * @param context Context
     * @return String?
     */
    fun getUUID(context: Context): String? {
        val deviceUuid = UUID(
            getDeviceId(context).hashCode().toLong(), getDeviceId(context).hashCode()
                .toLong() shl 32 or getSim(context).hashCode().toLong()
        )
        return deviceUuid.toString()
    }


    /**
     * tag device：判断是否为小米设备
     * 判断是否为小米设备
     * @return Boolean
     */
    fun isXiaoMi(): Boolean {
        return ("xiaomi".equals(Build.BRAND, ignoreCase = true)
                || "xiaomi".equals(Build.MANUFACTURER, ignoreCase = true))
    }

    /**
     * tag deivce：判断是否为华为设备
     * 判断是否为华为设备
     * @return Boolean
     */
    fun isHuawei(): Boolean {
        return ("huawei".equals(Build.BRAND, ignoreCase = true)
                || "huawei".equals(Build.MANUFACTURER, ignoreCase = true))
    }

    /**
     * tag device：判断是否为魅族设备
     * 判断是否为魅族设备
     * @return Boolean
     */
    fun isMeizu(): Boolean {
        return ("meizu".equals(Build.BRAND, ignoreCase = true)
                || "meizu".equals(Build.MANUFACTURER, ignoreCase = true)
                || "22c4185e".equals(Build.BRAND, ignoreCase = true))
    }

    /**
     * tag device：判断是否是oppo设备
     * 判断是否是oppo设备
     * @return Boolean
     */
    fun isOppo(): Boolean {
        return ("oppo".equals(Build.BRAND, ignoreCase = true)
                || "oppo".equals(Build.MANUFACTURER, ignoreCase = true))
    }

    /**
     * tag device：判断是否是vivo设备
     * 判断是否是vivo设备
     * @return Boolean
     */
    fun isVivo(): Boolean {
        return ("vivo".equals(Build.BRAND, ignoreCase = true)
                || "vivo".equals(Build.MANUFACTURER, ignoreCase = true))
    }

}