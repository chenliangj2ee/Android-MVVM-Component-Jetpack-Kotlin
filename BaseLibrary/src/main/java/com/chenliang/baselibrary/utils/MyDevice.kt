package com.chenliang.baselibrary.utils

import android.annotation.SuppressLint
import android.content.Context
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

    fun getUUID(context: Context): String? {
        val deviceUuid = UUID(
            getDeviceId(context).hashCode().toLong(), getDeviceId(context).hashCode()
                .toLong() shl 32 or getSim(context).hashCode().toLong()
        )
        return deviceUuid.toString()
    }

}