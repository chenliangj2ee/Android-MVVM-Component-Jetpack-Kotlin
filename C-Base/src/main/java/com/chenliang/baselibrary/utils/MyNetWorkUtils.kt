package com.chenliang.baselibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import com.chenliang.baselibrary.BaseInit
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException

/**
 * @Project: MVVM-Component
 * @Package: com.chenliang.baselibrary.utils
 * @author: chenliang
 * @date: 2021/07/09
 */
class MyNetWorkUtils {

    companion object {
        /**
         * 网络类型 - 无连接
         */
        private const val NETWORK_TYPE_NO_CONNECTION = -1231545315
        private const val NETWORK_TYPE_WIFI = "wifi"
        private const val NETWORK_TYPE_3G = "eg"
        private const val NETWORK_TYPE_2G = "2g"
        private const val NETWORK_TYPE_WAP = "wap"
        private const val NETWORK_TYPE_UNKNOWN = "unknown"
        private const val NETWORK_TYPE_DISCONNECT = "disconnect"


        /**
         * tag network：获取本机IP地址
         * 获取本机IP地址
         *
         * @return null：没有网络连接
         */
        val ipAddress: String?
            get() {
                try {
                    var networkInterface: NetworkInterface
                    var inetAddress: InetAddress
                    val en = NetworkInterface.getNetworkInterfaces()
                    while (en.hasMoreElements()) {
                        networkInterface = en.nextElement()
                        val enumIpAddress = networkInterface.inetAddresses
                        while (enumIpAddress.hasMoreElements()) {
                            inetAddress = enumIpAddress.nextElement()
                            if (!inetAddress.isLoopbackAddress) {
                                return inetAddress.hostAddress.toString()
                            }
                        }
                    }
                    return null
                } catch (ex: SocketException) {
                    ex.printStackTrace()
                    return null
                }

            }


        /**
         * 获取当前网络的状态
         *
         * @param context 上下文
         * @return 当前网络的状态。具体类型可参照NetworkInfo.State.CONNECTED、NetworkInfo.State.CONNECTED.DISCONNECTED等字段。当前没有网络连接时返回null
         */
        private fun getCurrentNetworkState(context: Context): Boolean {
            @SuppressLint("MissingPermission")
            val infos =
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).allNetworkInfo

            infos?.forEach {
                if (it.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
            return false
        }


        /**
         * 获取当前网络的类型
         *
         * @param context 上下文
         * @return 当前网络的类型。具体类型可参照ConnectivityManager中的TYPE_BLUETOOTH、TYPE_MOBILE、TYPE_WIFI等字段。当前没有网络连接时返回NetworkUtils.NETWORK_TYPE_NO_CONNECTION
         */
        private fun getCurrentNetworkType(context: Context): Int {
            @SuppressLint("MissingPermission") val networkInfo = (context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager).activeNetworkInfo
            return networkInfo?.type ?: NETWORK_TYPE_NO_CONNECTION
        }


        /**
         * tag network：网络是否已经连接
         * 网络是否已经连接
         * @param context Context
         * @return Boolean
         */
        fun isConnected(): Boolean {
            return getCurrentNetworkState(BaseInit.con!!)
        }


        /**
         * tag network：判断当前网络是否已经断开
         * 判断当前网络是否已经断开
         * @param context Context
         * @return Boolean
         */
        fun isDisconnected(): Boolean {
            return getCurrentNetworkState(BaseInit.con!!)
        }


        /**
         * tag network：是否是手机网络
         * 是否是手机网络
         * @param context Context
         * @return Boolean
         */
        fun isMobile(): Boolean {
            return getCurrentNetworkType(BaseInit.con!!) == ConnectivityManager.TYPE_MOBILE
        }


        /**
         * tag network：判断当前网络的类型是否是Wifi
         * 判断当前网络的类型是否是Wifi
         * @param context Context
         * @return Boolean
         */
        fun isWifi(): Boolean {
            return getCurrentNetworkType(BaseInit.con!!) == ConnectivityManager.TYPE_WIFI
        }


        /**
         * 获取Wifi的状态，需要ACCESS_WIFI_STATE权限
         *
         * @param context 上下文
         * @return 取值为WifiManager中的WIFI_STATE_ENABLED、WIFI_STATE_ENABLING、WIFI_STATE_DISABLED、WIFI_STATE_DISABLING、WIFI_STATE_UNKNOWN之一
         * @throws Exception 没有找到wifi设备
         */
        @SuppressLint("MissingPermission")
        @Throws(Exception::class)
        private fun getWifiState(): Int {
            val wifiManager = BaseInit.con!!.getSystemService(
                Context.WIFI_SERVICE
            ) as WifiManager
            return wifiManager?.wifiState ?: throw Exception("wifi device not found!")
        }


        /**
         * tag network：判断Wifi是否打开，需要ACCESS_WIFI_STATE权限
         * 判断Wifi是否打开，需要ACCESS_WIFI_STATE权限
         * @param context Context
         * @return Boolean
         * @throws Exception
         */
        @Throws(Exception::class)
        fun isWifiOpen(context: Context): Boolean {
            val wifiState = getWifiState()
            return wifiState == WifiManager.WIFI_STATE_ENABLED || wifiState == WifiManager.WIFI_STATE_ENABLING
        }


        /**
         * tag network：打开wifi
         * 打开Wifi，需要CHANGE_WIFI_STATE权限
         * @param context Context
         * @param enable Boolean
         * @return Boolean
         * @throws Exception
         */
        @SuppressLint("MissingPermission")
        @Throws(Exception::class)
        fun openWifi(): Boolean {
            if (!isWifiOpen(BaseInit.con!!)) {
                (BaseInit.con!!.getSystemService(
                    Context.WIFI_SERVICE
                ) as WifiManager).isWifiEnabled = true
            }
            return true
        }


        /**
         * tag network：断移动网络是否打开
         * 断移动网络是否打开，需要ACCESS_NETWORK_STATE权限
         * @param context Context
         * @return Boolean
         */
        @SuppressLint("MissingPermission")
        fun isMobileEnable(): Boolean {
            return (BaseInit.con!!.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager).getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE
            )!!.isConnected
        }
    }
}
