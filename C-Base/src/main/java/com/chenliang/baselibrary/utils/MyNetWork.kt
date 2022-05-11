package com.chenliang.baselibrary.utils

import android.net.TrafficStats
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * 创建API
 */
fun <T> Any.initAPI(url: String, cla: Class<T>): T = MyNetWork.initRetrofit(url).create(cla)


const val apiCode = 1

object MyNetWork {
    private val timeUnit: TimeUnit = TimeUnit.SECONDS
    private const val connectTimeOut: Long = 10
    private const val readTimeOut: Long = 30
    private const val writeTimeOut: Long = 30

    /**1
     * 初始化Retrofit
     */
    fun initRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(initHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**2
     * tag==header
     * tag==token
     * tag==apiCode
     * 初始化OkHttpClient
     */
    private fun initHttpClient(): OkHttpClient {
        var builder = OkHttpClient.Builder()
            .connectTimeout(connectTimeOut, timeUnit)
            .readTimeout(readTimeOut, timeUnit)
            .writeTimeout(writeTimeOut, timeUnit)
            .addInterceptor { chain ->
                val original = chain.request()
                val newOriginal = addParam(original)
                var user = getBeanUser()
                val request = newOriginal.newBuilder()
//                    .header("token", "${user?.token}")
//                    .header("apiCode", apiCode.toString())
                    .method(newOriginal.method, newOriginal.body)
                    .build()
                chain.proceed(request)
            }
        val loggingInterceptor = HttpLoggingInterceptor()
        builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor(OkHttpProfilerInterceptor())
        return builder.build()
    }


    /**3
     * 统一添加参数
     */
    private fun addParam(oldRequest: Request): Request {
        var user = getBeanUser()
        var builder = oldRequest.url
            .newBuilder()
//            .setEncodedQueryParameter(
//                "token",
//                "${user?.token}"
//            );
        var newRequest = oldRequest.newBuilder()
            .method(oldRequest.method, oldRequest.body)
            .url(builder.build())
            .build();
        return newRequest;
    }

    fun getNetworkBytes() = TrafficStats.getTotalRxBytes()

}

