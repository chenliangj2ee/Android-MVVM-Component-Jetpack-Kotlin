package com.chenliang.baselibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
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
        private fun getCurrentNetworkState(context: Context): NetworkInfo.State? {
            @SuppressLint("MissingPermission") val networkInfo = (context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager).activeNetworkInfo
            return networkInfo?.state
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
         * 网络是否已经连接
         * @param context Context
         * @return Boolean
         */
        fun isConnected(context: Context): Boolean {
            return getCurrentNetworkState(context) == NetworkInfo.State.CONNECTED
        }


        /**
         * 网络是否正在连接
         * @param context Context
         * @return Boolean
         */
        fun isConnecting(context: Context): Boolean {
            return getCurrentNetworkState(context) == NetworkInfo.State.CONNECTING
        }


        /**
         * 判断当前网络是否已经断开
         * @param context Context
         * @return Boolean
         */
        fun isDisconnected(context: Context): Boolean {
            return getCurrentNetworkState(context) == NetworkInfo.State.DISCONNECTED
        }


        /**
         * 断当前网络是否正在断开
         * @param context Context
         * @return Boolean
         */
        fun isDisconnecting(context: Context): Boolean {
            return getCurrentNetworkState(context) == NetworkInfo.State.DISCONNECTING
        }


        /**
         * 是否是手机网络
         * @param context Context
         * @return Boolean
         */
        fun isMobile(context: Context): Boolean {
            return getCurrentNetworkType(context) == ConnectivityManager.TYPE_MOBILE
        }


        /**
         * 判断当前网络的类型是否是Wifi
         * @param context Context
         * @return Boolean
         */
        fun isWifi(context: Context): Boolean {
            return getCurrentNetworkType(context) == ConnectivityManager.TYPE_WIFI
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
        private fun getWifiState(context: Context): Int {
            val wifiManager = context.getSystemService(
                Context.WIFI_SERVICE
            ) as WifiManager
            return wifiManager?.wifiState ?: throw Exception("wifi device not found!")
        }


        /**
         * 判断Wifi是否打开，需要ACCESS_WIFI_STATE权限
         * @param context Context
         * @return Boolean
         * @throws Exception
         */
        @Throws(Exception::class)
        fun isWifiOpen(context: Context): Boolean {
            val wifiState = getWifiState(context)
            return wifiState == WifiManager.WIFI_STATE_ENABLED || wifiState == WifiManager.WIFI_STATE_ENABLING
        }


        /**
         * 打开Wifi，需要CHANGE_WIFI_STATE权限
         * @param context Context
         * @param enable Boolean
         * @return Boolean
         * @throws Exception
         */
        @SuppressLint("MissingPermission")
        @Throws(Exception::class)
        fun openWifi(context: Context, enable: Boolean): Boolean {
            if (isWifiOpen(context) != enable) {
                (context.getSystemService(
                    Context.WIFI_SERVICE
                ) as WifiManager).isWifiEnabled = enable
            }
            return true
        }


        /**
         * 断移动网络是否打开，需要ACCESS_NETWORK_STATE权限
         * @param context Context
         * @return Boolean
         */
        @SuppressLint("MissingPermission")
        fun isMobileEnable(context: Context): Boolean {
            return (context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager).getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE
            )!!.isConnected
        }
    }
}
